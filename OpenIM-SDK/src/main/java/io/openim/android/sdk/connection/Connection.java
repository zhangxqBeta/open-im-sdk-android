package io.openim.android.sdk.connection;

import android.os.SystemClock;
import android.util.Log;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.listener.OnConnListener;
import io.openim.android.sdk.utils.AsyncUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class Connection extends WebSocketClient {

    private static final int PONG_WAIT_SECONDS = 30;
    private static final int PING_PERIOD = PONG_WAIT_SECONDS * 9 / 10;
    private static final int MAX_MESSAGE_SIZE = 1024 * 1024;
    private static final int MAX_RECONNECT_ATTEMPTS = 300;


    private final OnConnListener listener;
    private final ReConnector reConnector;
    private HeartBeat heartBeat;

    private volatile boolean isConnected;

    public Connection(OnConnListener listener) throws URISyntaxException {
        super(new URI(IMConfig.getInstance().buildWsUrl()));
        this.listener = listener;
        reConnector = new ReConnector(this);
    }

    //WebSocketClient callback
    @Override
    public void onOpen(ServerHandshake handshakeData) {
        LogcatHelper.logDInDebug(String.format("websocket handshake status: %d, msg: %s", handshakeData.getHttpStatus(), handshakeData.getHttpStatusMessage()));
    }

    //WebSocketClient callback
    @Override
    public void onMessage(String message) {
        LogcatHelper.logDInDebug(String.format("websocket onMessage : %s", message));
    }

    //WebSocketClient callback
    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogcatHelper.logDInDebug(String.format("websocket onClose  code: %d, reason: %s, remote: %b", code, reason, remote));
    }

    //WebSocketClient callback
    @Override
    public void onError(Exception ex) {
        LogcatHelper.logDInDebug(String.format("websocket onError : %s", ex));
    }

    public void sendData() {
        sendPing();
    }

    public void sendWsPing() {
        sendPing();
    }


    public void connectToServer() {
        AsyncUtils.runOnConnectThread(() -> {
            if (isConnected()) {
                return;
            }
            disconnect();
            doConnect();
        });
    }


    private void doConnect() {
        if (listener != null) {
            listener.onConnecting();
        }
        try {
//            super.connect();
            boolean isSuccess = super.connectBlocking(10000, TimeUnit.MILLISECONDS);

            if (isSuccess) {
                LogcatHelper.logDInDebug("websocket connect Success");
                listener.onConnectSuccess();
                heartBeat = new HeartBeat();
                new Thread(heartBeat).start();
            } else {
                listener.onConnectFailed(-1, "websocket connect failed");
            }

        } catch (Exception e) {
//            throw new RuntimeException(e);
            //todo
            return;
        }

        reConnector.cancelReConnect();

        //登录
        LogcatHelper.logDInDebug("Start login");

    }

    public void disconnect() {
        if (!isConnected) {
            return;
        }
        super.close();
    }

    public boolean isConnected() {
        return isConnected;
    }


    private class HeartBeat implements Runnable {

        public int pingCount;                               // 累计心跳没有回应次数

        public long pongTime = System.currentTimeMillis();  // 上次心跳返回时间

        private boolean running = true;

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            LogcatHelper.logDInDebug("websocket - start heartbeat");
            while (running) {
                sendWsPing();
                LogcatHelper.logDInDebug("websocket - send ws ping");
                SystemClock.sleep(PING_PERIOD * 1000);
            }
            sendData();
        }
    }

}
