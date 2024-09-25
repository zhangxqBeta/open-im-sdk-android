package io.openim.android.sdk.internal.user;

import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.conversation.Syncer;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.LocalUser;
import io.openim.android.sdk.generics.ReturnWithErr;
import io.openim.android.sdk.http.ApiClient;
import io.openim.android.sdk.http.ServerApiRouter;
import io.openim.android.sdk.internal.cache.Cache;
import io.openim.android.sdk.listener.OnUserListener;
import io.openim.android.sdk.protos.sdkws.UserInfo;
import io.openim.android.sdk.protos.user.GetDesignateUsersReq;
import io.openim.android.sdk.protos.user.GetDesignateUsersResp;
import io.openim.android.sdk.protos.user.OnlineStatus;
import io.openim.android.sdk.utils.BatchUtil;
import io.openim.android.sdk.utils.ConvertUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

    //todo: not implemented for now
    private OnUserListener listener;
    private Cache<String, BasicInfo> userBasicCache;
    private Cache<String, OnlineStatus> onlineStatusCache;
    private Syncer<LocalUser, String> userSyncer;


    public static User getInstance() {
        return User.SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static User instance = new User();
    }


    private User() {
        userBasicCache = new Cache<>();
        onlineStatusCache = new Cache<>();
//        initSyncer();
    }


    public Syncer<LocalUser, String> getUserSyncer() {
        return userSyncer;
    }

    public OnUserListener getListener() {
        return listener;
    }

    public void setListener(OnUserListener listener) {
        this.listener = listener;
    }

    public Cache<String, BasicInfo> getUserBasicCache() {
        return userBasicCache;
    }

    public void setUserBasicCache(
        Cache<String, BasicInfo> userBasicCache) {
        this.userBasicCache = userBasicCache;
    }

    public Cache<String, OnlineStatus> getOnlineStatusCache() {
        return onlineStatusCache;
    }

    public void setOnlineStatusCache(
        Cache<String, OnlineStatus> onlineStatusCache) {
        this.onlineStatusCache = onlineStatusCache;
    }

    public void initSyncer() {
        var localUserDao = ChatDbManager.getInstance().getImDatabase().localUserDao();
        userSyncer = new Syncer<>(
            (LocalUser value) -> {
                localUserDao.insert(value);
                return null;
            },
            (LocalUser value) -> {
                return new Exception("not support delete user: " + value.userID);
            },
            (LocalUser serverUser, LocalUser localUser) -> {
                int n = localUserDao.update(serverUser);
                if (n == 0) {
                    return new Exception("update user failed");
                }
                return null;
            },
            (LocalUser user) -> {
                return user.userID;
            },
            null,
            (Integer state, LocalUser server, LocalUser local) -> {
                switch (state) {
                    case Syncer.UpdateState:
                        listener.onSelfInfoUpdated(ConvertUtil.convertToUserInfo(server));
                        //golang impl, skip for now
                        // if server.Nickname != local.Nickname || server.FaceURL != local.FaceURL {
                        //					_ = common.TriggerCmdUpdateMessage(ctx, common.UpdateMessageNode{Action: constant.UpdateMsgFaceUrlAndNickName,
                        //						Args: common.UpdateMessageInfo{SessionType: constant.SingleChatType, UserID: server.UserID, FaceURL: server.FaceURL, Nickname: server.Nickname}}, u.conversationCh)
                        //				}
                }
                return null;
            }
        );
    }

    public static ReturnWithErr<io.openim.android.sdk.database.LocalUser> getSelfUserInfo() {
        var loginUserID = IMConfig.getInstance().userID;
        var userInfo = ChatDbManager.getInstance().getImDatabase().localUserDao().getLoginUser(loginUserID);
        if (userInfo == null) {
            var returnWithErr = getServerUserInfo(Arrays.asList(loginUserID));
            if (returnWithErr.hasError()) {
                return new ReturnWithErr<>(returnWithErr.getError());
            }

            var srvUserInfo = returnWithErr.getPayload();
            if (srvUserInfo.size() == 0) {
                return new ReturnWithErr<>(new Exception("UserIDNotFoundError"));
            }

            userInfo = ConvertUtil.convertToLocalUser(srvUserInfo.get(0));
            ChatDbManager.getInstance().getImDatabase().localUserDao().insert(userInfo);
        }

        return new ReturnWithErr<>(userInfo);
    }

    public static ReturnWithErr<List<UserInfo>> getServerUserInfo(List<String> userIDs) {
        var req = GetDesignateUsersReq.newBuilder().addAllUserIDs(userIDs).build();
        var jsonReq = ConvertUtil.protobufToJsonStr(req);

        var returnWithErr = ApiClient.callApi(ServerApiRouter.GetUsersInfoRouter, jsonReq,
            GetDesignateUsersResp.class);
        if (returnWithErr.getError() != null) {
            return new ReturnWithErr<>(returnWithErr.getError());
        }

        return new ReturnWithErr<>(returnWithErr.getPayload().getUsersInfoList());

    }

    public static Exception syncLoginUserInfo() {
        var loginUserID = IMConfig.getInstance().userID;
        var returnWithErr = getSingleUserFromSvr(loginUserID);
        if (returnWithErr.hasError()) {
            return returnWithErr.getError();
        }
        var remoteUser = returnWithErr.getPayload();
        var localUser = ChatDbManager.getInstance().getImDatabase().localUserDao().getLoginUser(loginUserID);

        List<LocalUser> localUsers = new ArrayList<>();
        localUsers.add(localUser);

        return User.getInstance().getUserSyncer().sync(Arrays.asList(remoteUser), localUsers, null);
    }

    public static ReturnWithErr<LocalUser> getSingleUserFromSvr(String userID) {
        var returnWithErr = getUsersInfoFromSvr(Arrays.asList(userID));
        if (returnWithErr.hasError()) {
            return new ReturnWithErr<>(returnWithErr.getError());
        }

        var users = returnWithErr.getPayload();
        if (users.size() > 0) {
            return new ReturnWithErr<>(users.get(0));
        }
        return new ReturnWithErr<>(new Exception(String.format("getSelfUserInfo failed, userID: %s not exist", userID)));
    }

    public static ReturnWithErr<List<LocalUser>> getUsersInfoFromSvr(List<String> userIDs) {

        var req = GetDesignateUsersReq.newBuilder().addAllUserIDs(userIDs).build();
        var jsonReq = ConvertUtil.protobufToJsonStr(req);

        var returnWithErr = ApiClient.callApi(ServerApiRouter.GetUsersInfoRouter, jsonReq,
            GetDesignateUsersResp.class);
        if (returnWithErr.hasError()) {
            return new ReturnWithErr<>(returnWithErr.getError());
        }
        var resp = returnWithErr.getPayload();
        return new ReturnWithErr<>(BatchUtil.batch(ConvertUtil::convertToLocalUser, resp.getUsersInfoList()));
    }

}
