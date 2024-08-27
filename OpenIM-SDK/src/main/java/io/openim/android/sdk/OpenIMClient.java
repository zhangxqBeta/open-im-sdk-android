package io.openim.android.sdk;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.common.SdkException;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.connection.ConnectionManager;
import io.openim.android.sdk.conversation.Conversation;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.database.LocalSendingMessage;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.LoginStatus;
import io.openim.android.sdk.enums.ReqIdentifier;
import io.openim.android.sdk.handler.MsgSyncer;
import io.openim.android.sdk.internal.user.User;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.protos.push.DelUserPushTokenReq;
import io.openim.android.sdk.protos.push.DelUserPushTokenResp;
import io.openim.android.sdk.utils.AsyncUtils;
import io.openim.android.sdk.utils.SpUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.listener.BaseImpl;
import io.openim.android.sdk.listener.OnBase;
import io.openim.android.sdk.listener.OnConnListener;
import io.openim.android.sdk.listener.OnCustomBusinessListener;
import io.openim.android.sdk.listener.OnPutFileListener;
import io.openim.android.sdk.listener._ConnListener;
import io.openim.android.sdk.listener._CustomBusinessListener;
import io.openim.android.sdk.listener._PutFileCallback;
import io.openim.android.sdk.manager.ConversationManager;
import io.openim.android.sdk.manager.FriendshipManager;
import io.openim.android.sdk.manager.GroupManager;
import io.openim.android.sdk.manager.MessageManager;
import io.openim.android.sdk.manager.UserInfoManager;
import io.openim.android.sdk.models.InitConfig;
import io.openim.android.sdk.models.PutArgs;
import io.openim.android.sdk.utils.CommonUtil;
import io.openim.android.sdk.utils.JsonUtil;
import io.openim.android.sdk.utils.ParamsUtil;
import open_im_sdk.Open_im_sdk;
import open_im_sdk_callback.Base;
import open_im_sdk_callback.UploadLogProgress;

public class OpenIMClient {

    public ConversationManager conversationManager;
    public FriendshipManager friendshipManager;
    public GroupManager groupManager;
    public MessageManager messageManager;
    public UserInfoManager userInfoManager;

    public Application app;

    /*native impl*/
    private IMConfig imConfig;

    /*native impl*/
    private OnConnListener connListener;

    /*native impl*/
    private int loginStatus;

    private BlockingQueue<Cmd2Value> conversationCh;
    //    private BlockingQueue<Cmd2Value> heartbeatCmdCh;
    private BlockingQueue<Cmd2Value> pushMsgAndMaxSeqCh;
    private BlockingQueue<Cmd2Value> loginMgrCh;

    private OpenIMClient() {
        conversationManager = new ConversationManager();
        friendshipManager = new FriendshipManager();
        groupManager = new GroupManager();
        messageManager = new MessageManager();
        userInfoManager = new UserInfoManager();
        imConfig = IMConfig.getInstance();

    }

    private static class Singleton {

        private static final OpenIMClient INSTANCE = new OpenIMClient();
    }

    public static OpenIMClient getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 初始化sdk 注：在创建图片，语音，视频，文件等需要路径参数的消息体时， 如果选择的是非全路径方法如：createSoundMessage（全路径方法为：createSoundMessageFromFullPath）, 需要将文件自行拷贝到dbPath目录下，如果此时文件路径为
     * apath+"/sound/a.mp3"，则参数path的值为：/sound/a.mp3。 如果选择的全路径方法，路径为你文件的实际路径不需要再拷贝。
     *
     * @param listener SDK初始化监听
     * @return boolean   true成功; false失败
     */
    public boolean initSDK(Application application, InitConfig initConfig, @NotNull OnConnListener listener) {
        this.app = application;

        imConfig.useNativeImpl = initConfig.useNativeImpl;
        imConfig.apiAddr = initConfig.apiAddr;
        imConfig.dataDir = initConfig.dataDir;
        imConfig.wsAddr = initConfig.wsAddr;
        if (imConfig.useNativeImpl) {
            return initSDKNativeImpl(listener);
        } else {
            boolean initialized = Open_im_sdk.initSDK(new _ConnListener(listener),
                ParamsUtil.buildOperationID(), JsonUtil.toString(initConfig));
            LogcatHelper.logDInDebug(String.format("Initialization successful: %s", initialized));
            return initialized;
        }

    }

    private boolean initSDKNativeImpl(@NotNull OnConnListener listener) {
        connListener = listener;
        initResources();
        return true;
    }

    private void initResources() {
        conversationCh = new LinkedBlockingQueue<>(1000);
//        heartbeatCmdCh = new LinkedBlockingQueue<>(10);
        pushMsgAndMaxSeqCh = new LinkedBlockingQueue<>(1000);
        loginMgrCh = new LinkedBlockingQueue<>(1);
//        var chatDbManager = ChatDbManager.getInstance();
//        chatDbManager.init(app, IMConfig.getInstance().userID);

        //channel
        setLoginStatus(LoginStatus.Logout);
    }

    public BlockingQueue<Cmd2Value> getConversationCh() {
        return conversationCh;
    }

    public void setConversationCh(
        BlockingQueue<Cmd2Value> conversationCh) {
        this.conversationCh = conversationCh;
    }

    public BlockingQueue<Cmd2Value> getPushMsgAndMaxSeqCh() {
        return pushMsgAndMaxSeqCh;
    }

    public void setPushMsgAndMaxSeqCh(
        BlockingQueue<Cmd2Value> pushMsgAndMaxSeqCh) {
        this.pushMsgAndMaxSeqCh = pushMsgAndMaxSeqCh;
    }

    public BlockingQueue<Cmd2Value> getLoginMgrCh() {
        return loginMgrCh;
    }

    public void setLoginMgrCh(
        BlockingQueue<Cmd2Value> loginMgrCh) {
        this.loginMgrCh = loginMgrCh;
    }

    public void unInit() {
        Open_im_sdk.unInitSDK(ParamsUtil.buildOperationID());
    }

    private void registerNetworkCallback() {
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//API 大于26时
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {//API 大于21时
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }

    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            //网络可用的时候调用
            networkChanged();
        }

        @Override
        public void onLosing(@NonNull Network network, int maxMsToLive) {
            //网络正在减弱，链接会丢失数据，即将断开网络时调用
            networkChanged();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            //网络功能发生改变时调用
            networkChanged();
        }

        @Override
        public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
            //网络连接属性发生改变时调用
            networkChanged();
        }
    };


    private void registerActivityLifecycleCallbacks() {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                setAppBackgroundStatus(false);
            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                setAppBackgroundStatus(true);
            }
        });
    }

    private static void setAppBackgroundStatus(boolean isBackground) {
        Open_im_sdk.setAppBackgroundStatus(new Base() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(String s) {

            }
        }, ParamsUtil.buildOperationID(), isBackground);
    }

    /**
     * 登录 注：调用此方法前请先注册各类监听如：OnAdvanceMsgListener、OnFriendshipListener、OnConversationListener、OnGroupListener等...
     *
     * @param uid 用户id
     * @param token 用户token
     * @param base callback String
     */
    public void login(@NotNull OnBase<String> base, String uid, String token) {
        if (imConfig.useNativeImpl) {
            if (getLoginStatus() == LoginStatus.Logged) {
                CommonUtil.returnError(base, SdkException.ErrLoginRepeat.getCode(), SdkException.ErrLoginRepeat.getMessage());
                return;
            }

            setLoginStatus(LoginStatus.Logging);

            imConfig.userID = uid;
            imConfig.token = token;
            SpUtils.putToken(token);
            SpUtils.putUid(uid);
            //init ws
            ConnectionManager.getInstance().connect();

            //init db, move to init
            var chatDbManager = ChatDbManager.getInstance();
            chatDbManager.init(app, uid);
            checkSendingMessage();
            // golang impl:
            // u.user = user.NewUser(u.db, u.loginUserID, u.conversationCh)
            User.getInstance().initSyncer();
            //invoke loadSeq internally
            MsgSyncer.getInstance().loadSeq();

            run();
            setLoginStatus(LoginStatus.Logged);
            CommonUtil.returnSuccess(base, "");
            return;
        }

        Open_im_sdk.login(new Base() {
            @Override
            public void onError(int i, String s) {
                CommonUtil.returnError(base, i, s);
            }

            @Override
            public void onSuccess(String s) {
                CommonUtil.runMainThread(() -> {
//                   registerActivityLifecycleCallbacks();
//                   registerNetworkCallback();
                    base.onSuccess(s);
                });
            }
        }, ParamsUtil.buildOperationID(), uid, token);
    }

    private void run() {
        // golang impl:u.longConnMgr.Run(ctx)
        //go c.readPump(ctx)
        //	go c.writePump(ctx)
        //	go c.heartbeat(ctx)
        // has been implemented in java Connection.

        AsyncUtils.runOnListenerThread(() -> {
            MsgSyncer.getInstance().doListener();
        });
        AsyncUtils.runOnListenerThread(() -> {
            //golang impl:	go common.DoListener(u.conversation, u.ctx)
            Conversation.getInstance().doListener();
        });

        //golang impl: go u.group.DeleteGroupAndMemberInfo(ctx)

        AsyncUtils.runOnListenerThread(() -> {
            //golang impl:	go common.DoListener(u.conversation, u.ctx)
            logoutListener();
        });
    }

    public void logoutListener() {
        while (true) {
            try {
                Cmd2Value cmd = loginMgrCh.take();
                logout(new OnBase<String>() {
                    @Override
                    public void onError(int code, String error) {
                        OnBase.super.onError(code, error);
                    }

                    @Override
                    public void onSuccess(String data) {
                        OnBase.super.onSuccess(data);
                    }
                });
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * native impl. golang impl: returned error, however, it's not handled upper-level; so return void in java
     */
    private void checkSendingMessage() {
        var localSendingMessageDao = ChatDbManager.getInstance().getImDatabase().localSendingMessageDao();
        var sendingMessages = localSendingMessageDao.getAll();
        for (var message : sendingMessages) {
            handlerSendingMsg(message);
            localSendingMessageDao.delete(new LocalSendingMessage(message.conversationID, message.clientMsgID));
        }
    }

    /**
     * native impl.
     */
    private void handlerSendingMsg(LocalSendingMessage sendingMsg) {
        var imDatabase = ChatDbManager.getInstance().getImDatabase();
        var localChatLogDao = imDatabase.chatLogDao();
        var localConversationDao = imDatabase.localConversationDao();
        var tableMessage = localChatLogDao.getMessage(sendingMsg.conversationID, sendingMsg.clientMsgID);
        if (tableMessage == null) {
            return;
        }
        if (tableMessage.status != Constants.MSG_STATUS_SENDING) {
            return;
        }

        var localChatLog = new ChatLog();
        localChatLog.conversationID = sendingMsg.conversationID;
        localChatLog.clientMsgID = sendingMsg.clientMsgID;
        localChatLog.status = Constants.MSG_STATUS_SEND_FAILED;
        int rowsAffected = localChatLogDao.update(localChatLog);
        if (rowsAffected == 0) {
            return;
        }

        var conversation = localConversationDao.getConversation(sendingMsg.conversationID);
        var latestMsg = JsonUtil.toObj(conversation.latestMsg, Message.class);
        if (latestMsg.getClientMsgID().equals(sendingMsg.clientMsgID)) {
            latestMsg.setStatus(Constants.MSG_STATUS_SEND_FAILED);
            conversation.latestMsg = JsonUtil.toString(latestMsg);
            localConversationDao.update(conversation);
        }

    }

    /**
     * 登出
     */
    public void logout(OnBase<String> base) {
        if (imConfig.useNativeImpl) {
            var delUserPushTokenReq = DelUserPushTokenReq.newBuilder().setUserID(imConfig.userID).setPlatformID(imConfig.platformID).build();
            ConnectionManager.getInstance().getConnection().sendReqWaitResp(ReqIdentifier.LOGOUT_MSG, delUserPushTokenReq, DelUserPushTokenResp.parser());
            setLoginStatus(LoginStatus.Logout);
            CommonUtil.returnSuccess(base, null);
            return;
        }
        Open_im_sdk.logout(BaseImpl.stringBase(base), ParamsUtil.buildOperationID());
    }

    protected void setLoginStatus(int status) {
        loginStatus = status;
    }

    /**
     * 查询登录状态
     */
    public int getLoginStatus() {
        return (int) Open_im_sdk.getLoginStatus(ParamsUtil.buildOperationID());
    }

    /**
     * 查询已登录 uid
     */
    public String getLoginUserID() {
        return Open_im_sdk.getLoginUserID();
    }


    /**
     * 上传文件到服务器
     *
     * @param putArgs 实体
     */
    public void uploadFile(OnBase<String> base, OnPutFileListener listener,
        PutArgs putArgs) {
        Open_im_sdk.uploadFile(BaseImpl.stringBase(base),
            ParamsUtil.buildOperationID(), JsonUtil.toString(putArgs), new _PutFileCallback(listener));
    }

    /**
     * 上传出错日志
     */
    public void uploadLogs(OnBase<String> base, List<String> params, String ex, UploadLogProgress progress) {
        Open_im_sdk.uploadLogs(BaseImpl.stringBase(base), ParamsUtil.buildOperationID(), ex, progress);
    }


    /**
     * 更新firebase token
     *
     * @param fcmToken token
     */
    public void updateFcmToken(OnBase<String> base, String fcmToken, long expireTime) {
        Open_im_sdk.updateFcmToken(BaseImpl.stringBase(base), ParamsUtil.buildOperationID(), fcmToken, expireTime);
    }


    public void networkChanged() {
        Open_im_sdk.networkStatusChanged(new Base() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(String s) {

            }
        }, ParamsUtil.buildOperationID());
    }

    /**
     * 自定义业务通知
     */
    public void setCustomBusinessListener(OnCustomBusinessListener customBusinessListener) {
        Open_im_sdk.setCustomBusinessListener(new _CustomBusinessListener(customBusinessListener));
    }
}

