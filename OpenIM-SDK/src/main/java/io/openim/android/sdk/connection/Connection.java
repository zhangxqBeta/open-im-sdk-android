package io.openim.android.sdk.connection;

import android.os.SystemClock;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.SdkException;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.conversation.Trigger;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.ReqIdentifier;
import io.openim.android.sdk.generics.ReturnWithSdkErr;
import io.openim.android.sdk.interaction.GeneralWsReq;
import io.openim.android.sdk.interaction.GeneralWsResp;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.listener.OnConnListener;
import io.openim.android.sdk.models.CmdMaxSeqToMsgSync;
import io.openim.android.sdk.protos.sdkws.GetMaxSeqReq;
import io.openim.android.sdk.protos.sdkws.GetMaxSeqResp;
import io.openim.android.sdk.protos.sdkws.PushMessages;
import io.openim.android.sdk.utils.AsyncUtils;
import io.openim.android.sdk.utils.GzipUtil;
import io.openim.android.sdk.utils.JsonUtil;
import io.openim.android.sdk.utils.ParamsUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class Connection extends WebSocketClient {

    private static final int PONG_WAIT_SECONDS = 30;
    private static final int PING_PERIOD = PONG_WAIT_SECONDS * 9 / 10;

    private static final int ON_MESSAGE_THREAD_NUM_LIMIT = 4;
    private static final int RESPONSE_BLOCKING_TIMEOUT = 5; //in seconds

    private static final int CONNECT_NETWORK_ERROR = 10000;
    private static final int RECONNECT_TIMEOUT_IN_MS = 10000;


    private final OnConnListener listener;
    private HeartBeat heartBeat;
    private ExponentialRetryStrategy exponentialRetryStrategy;

    private ExecutorService onMessageExecutor;
    private ConcurrentHashMap<String, BlockingQueue<GeneralWsResp>> responseQueues;

    private int connStatus;

    public Connection(OnConnListener listener) throws URISyntaxException {
        super(new URI(IMConfig.getInstance().buildWsUrl()));
        this.listener = listener;
        exponentialRetryStrategy = new ExponentialRetryStrategy();
        onMessageExecutor = Executors.newFixedThreadPool(ON_MESSAGE_THREAD_NUM_LIMIT);
        responseQueues = new ConcurrentHashMap<>();
    }

    public OnConnListener getListener() {
        return listener;
    }

    //WebSocketClient callback
    @Override
    public void onOpen(ServerHandshake handshakeData) {
        LogcatHelper.logDInDebug(String.format("websocket handshake status: %d, msg: %s", handshakeData.getHttpStatus(), handshakeData.getHttpStatusMessage()));
    }

    //WebSocketClient callback
    @Override
    public void onMessage(String message) {

    }

    //WebSocketClient callback
    @Override
    public void onMessage(ByteBuffer bytes) {
        handleMessage(bytes);
    }

    private void handleMessage(ByteBuffer bytes) {
        LogcatHelper.logDInDebug(String.format("websocket onMessage[bytes] : %s", bytes));
        byte[] decompressedBytes = GzipUtil.decompress(bytes.array());
        LogcatHelper.logDInDebug(String.format("websocket receive jsonStr from server : %s", new String(decompressedBytes)));
        GeneralWsResp wsResp = JsonUtil.toObj(new String(decompressedBytes), GeneralWsResp.class);
        if (wsResp.getErrCode() != 0) {
            LogcatHelper.logDInDebug(String.format("websocket receive message err. errCode: %d, errMsg: %s", wsResp.getErrCode(), wsResp.getErrMsg()));
        }

        switch (wsResp.reqIdentifier) {
            case ReqIdentifier.PUSH_MSG:
                doPushMsg(wsResp);
                break;
            case ReqIdentifier.KICK_ONLINE_MSG:
                ConnectionManager.getInstance().onKickedOffline();
                Trigger.triggerCmdLogOut(OpenIMClient.getInstance().getLoginMgrCh());
                break;

            case ReqIdentifier.LOGOUT_MSG:
            case ReqIdentifier.GET_NEWEST_SEQ:
            case ReqIdentifier.PULL_MSG_BY_SEQ_LIST:
            case ReqIdentifier.SEND_MSG:
            case ReqIdentifier.SEND_SIGNAL_MSG:
            case ReqIdentifier.SET_BACKGROUND_STATUS:
                //golang impl:
                // err := c.Syncer.NotifyResp(ctx, wsResp)
                onMessageExecutor.submit(() -> {
                    if (wsResp.getOperationID() != null) {
                        BlockingQueue<GeneralWsResp> queue = responseQueues.get(wsResp.getOperationID());
                        if (queue != null) {
                            queue.add(wsResp);
                        }
                    }
                });
                break;
        }

    }


    public Exception doPushMsg(GeneralWsResp resp) {
        LogcatHelper.logDInDebug(String.format("websocket doPushMsg handler"));
        var protoData = resp.getData();
        try {
            var msg = PushMessages.parseFrom(protoData);
            LogcatHelper.logDInDebug(String.format("websocket doPushMsg msg: %s", msg.toString()));
            return Trigger.triggerCmdPushMsg(msg, OpenIMClient.getInstance().getPushMsgAndMaxSeqCh());
        } catch (InvalidProtocolBufferException e) {
            LogcatHelper.logDInDebug(String.format("websocket proto parse error : %s", e));
            return e;
        }
    }

    //WebSocketClient callback
    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogcatHelper.logDInDebug(String.format("websocket connect onClose  code: %d, reason: %s, remote: %b", code, reason, remote));
        reConnect(true);
    }

    //WebSocketClient callback
    @Override
    public void onError(Exception ex) {
        LogcatHelper.logDInDebug(String.format("websocket connect onError : %s", ex));
        reConnect(true);
    }

    public void sendPingToServer() {
        var m = GetMaxSeqReq.newBuilder().setUserID(IMConfig.getInstance().userID).build();
        var wsSeqResp = sendReqWaitResp(ReqIdentifier.GET_NEWEST_SEQ, m, GetMaxSeqResp.parser());
        if (wsSeqResp.hasError()) {
            LogcatHelper.logDInDebug(String.format("websocket connect - sendPingToServer error : %s", wsSeqResp.getError()));
            return;
        }
        LogcatHelper.logDInDebug(String.format("websocket connect - receive pong!"));
        var cmd = new CmdMaxSeqToMsgSync();
        cmd.conversationMaxSeqOnSvr = wsSeqResp.getPayload().getMaxSeqsMap();
        Trigger.triggerCmdMaxSeq(cmd, OpenIMClient.getInstance().getPushMsgAndMaxSeqCh());
    }

    public <T extends GeneratedMessageLite> ReturnWithSdkErr<T> sendReqWaitResp(int reqIdentifier, AbstractMessageLite protoReq, Parser<T> parser) {
        String userId = IMConfig.getInstance().userID;
        byte[] data = protoReq.toByteArray();
        LogcatHelper.logDInDebug(String.format("websocket sendWsPingToServer proto data : %s", new String(data)));
        String opID = ParamsUtil.buildOperationID();
        String msgIncr = ParamsUtil.genMsgIncr(userId);
        LogcatHelper.logDInDebug(String.format("websocket sendWsPingToServer opID : %s, msgIncr: %s", opID, msgIncr));
        GeneralWsReq req = new GeneralWsReq(reqIdentifier, userId, opID, msgIncr, data);
        var jsonStr = JsonUtil.toString(req);
        LogcatHelper.logDInDebug(String.format("websocket send jsonStr to server : %s", jsonStr));
        var gzipBytes = GzipUtil.compress(jsonStr.getBytes());
        if (!isConnected()) {
            LogcatHelper.logDInDebug(String.format("websocket connect sendWsPingToServer not connected"));
            return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, "ws not connected"));
        }
        send(gzipBytes);

        BlockingQueue<GeneralWsResp> responseQueue = new LinkedBlockingQueue<>();
        responseQueues.put(opID, responseQueue);
        LogcatHelper.logDInDebug(String.format("websocket sendReqWaitResp responseQueues put opID : %s", opID));
        try {
            GeneralWsResp resp = responseQueue.poll(RESPONSE_BLOCKING_TIMEOUT, TimeUnit.SECONDS);
            if (resp == null) {
                LogcatHelper.logDInDebug(String.format("websocket sendReqWaitResp responseQueues resp is null"));
                return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, "resp is null"));
            }
            if (resp.getErrCode() != 0) {
                LogcatHelper.logDInDebug(String.format("websocket sendReqWaitResp resp error:  not connected"));
                return new ReturnWithSdkErr<>(new SdkException(resp.getErrCode(), resp.getErrMsg()));
            }
            return new ReturnWithSdkErr<>(parser.parseFrom(resp.getData()));
        } catch (InterruptedException e) {
            return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, "response channel closed"));
        } catch (InvalidProtocolBufferException e) {
            return new ReturnWithSdkErr<>(SdkException.ErrArgs);
        } finally {
            responseQueues.remove(opID);
        }
    }

    public void connectToServer() {
        AsyncUtils.runOnConnectThread(() -> {
            if (isConnected()) {
                return;
            }
            disconnect();
            doConnect(false);
        });
    }

    public void reConnect(boolean force) {
        AsyncUtils.runOnConnectThread(() -> {
            //如果强制重连，则不检查当前连接状态
            if (!force && isConnected()) {
                LogcatHelper.logDInDebug("websocket connect, reConnect hit return: !force && isConnected()");
                return;
            }
            int sleepInterval = exponentialRetryStrategy.getSleepIntervalInMs();
            LogcatHelper.logDInDebug("websocket connect: " + sleepInterval + "毫秒之后开始重连");
            SystemClock.sleep(sleepInterval);
            doConnect(true);
        });
    }


    private void doConnect(boolean isReconnect) {
        //check existing connection
        if (isReconnect) {
            if (getConnection() != null && getConnection().isOpen()) {
                LogcatHelper.logDInDebug("websocket connect - is already connected ");
                return;
            }
        }

        if (listener != null) {
            setConnectionStatus(Constants.CONNECTING);
            listener.onConnecting();
        }

        try {
            boolean isSuccess = false;
            if (isReconnect) {
                LogcatHelper.logDInDebug("websocket reconnect start ");
                isSuccess = super.reconnectBlocking();
            } else {
                LogcatHelper.logDInDebug("websocket connect start ");
                isSuccess = super.connectBlocking(RECONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
            }

            if (isSuccess) {
                setConnectionStatus(Constants.CONNECTED);
                LogcatHelper.logDInDebug("websocket connect Success. IsReconnect: " + isReconnect);
                listener.onConnectSuccess();
                if (!isReconnect) {
                    heartBeat = new HeartBeat();
                    new Thread(heartBeat).start();
                }
                exponentialRetryStrategy.reset();
            } else {
                setConnectionStatus(Constants.DEFAULT_NOT_CONNECT);
                listener.onConnectFailed(CONNECT_NETWORK_ERROR, "websocket connect failed. IsReconnect: " + isReconnect);
                //重连
                reConnect(false);
            }

        } catch (Exception e) {
            LogcatHelper.logDInDebug("websocket connect exception:  " + e);
            return;
        }
        //登录
        LogcatHelper.logDInDebug("Start login");

    }

    public void disconnect() {
        if (!isConnected()) {
            return;
        }
        setConnectionStatus(Constants.CLOSED);
    }

    public synchronized boolean isConnected() {
        if (connStatus == Constants.CONNECTED) {
            return true;
        }
        return false;
    }

    public synchronized void setConnectionStatus(int status) {
        connStatus = status;
    }


    private class HeartBeat implements Runnable {

        private boolean running = true;

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            LogcatHelper.logDInDebug("websocket connect - start heartbeat");
            //heartbeat
            while (running) {
//                sendWsPingToServer();
                LogcatHelper.logDInDebug("websocket connect - try send ws pingToServer");
                sendPingToServer();
                SystemClock.sleep(PING_PERIOD * 1000);
            }
        }
    }

}
