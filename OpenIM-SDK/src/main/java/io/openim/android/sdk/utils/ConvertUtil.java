package io.openim.android.sdk.utils;

import com.google.protobuf.ByteString;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.database.LocalErrChatLog;
import io.openim.android.sdk.database.LocalUser;
import io.openim.android.sdk.enums.ConversationType;
import io.openim.android.sdk.models.ConversationInfo;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.models.OfflinePushInfo;
import io.openim.android.sdk.protos.sdkws.MsgData;
import io.openim.android.sdk.protos.sdkws.UserInfo;
import java.util.Arrays;

public class ConvertUtil {

    public static ConversationInfo convertToConversationInfo(LocalConversation localConversation) {
        if (localConversation == null) {
            return null;
        }
        ConversationInfo conversationInfo = new ConversationInfo();
        conversationInfo.setConversationID(localConversation.conversationID);
        conversationInfo.setConversationType(localConversation.conversationType);
        conversationInfo.setUserID(localConversation.userID);
        conversationInfo.setGroupID(localConversation.groupID);
        conversationInfo.setShowName(localConversation.showName);
        conversationInfo.setFaceURL(localConversation.faceURL);
        conversationInfo.setRecvMsgOpt(localConversation.recvMsgOpt);
        conversationInfo.setUnreadCount(localConversation.unreadCount);
        conversationInfo.setGroupAtType(localConversation.groupAtType);
        conversationInfo.setLatestMsg(localConversation.latestMsg);
        conversationInfo.setLatestMsgSendTime(localConversation.latestMsgSendTime);
        conversationInfo.setDraftText(localConversation.draftText);
        conversationInfo.setDraftTextTime(localConversation.draftTextTime);
        conversationInfo.setPinned(localConversation.isPinned);
        conversationInfo.setPrivateChat(localConversation.isPrivateChat);
        conversationInfo.setBurnDuration(localConversation.burnDuration);
        conversationInfo.setNotInGroup(localConversation.isNotInGroup);
        conversationInfo.setEx(localConversation.ex);
        conversationInfo.setMsgDestructTime(localConversation.msgDestructTime);
        conversationInfo.setMsgDestruct(localConversation.isMsgDestruct);
        return conversationInfo;
    }

    public static ChatLog convertToLocalChatLog(MsgData msgData) {
        ChatLog localChatLog = new ChatLog();
        localChatLog.clientMsgID = msgData.getClientMsgID();
        localChatLog.serverMsgID = msgData.getServerMsgID();
        localChatLog.senderPlatformID = msgData.getSenderPlatformID();
        localChatLog.sendID = msgData.getSendID();
        localChatLog.recvID = msgData.getRecvID();
        localChatLog.senderNickname = msgData.getSenderNickname();
        localChatLog.senderFaceURL = msgData.getSenderFaceURL();
        localChatLog.sessionType = msgData.getSessionType();
        localChatLog.msgFrom = msgData.getMsgFrom();
        localChatLog.contentType = msgData.getContentType();
        localChatLog.content = msgData.getContent().toString();
        localChatLog.isRead = msgData.getIsRead();
        localChatLog.status = msgData.getStatus();
        localChatLog.seq = msgData.getSeq();
        localChatLog.sendTime = msgData.getSendTime();
        localChatLog.createTime = msgData.getCreateTime();
        localChatLog.attachedInfo = msgData.getAttachedInfo();
        localChatLog.ex = msgData.getEx();
        return localChatLog;
    }

    public static ChatLog convertToLocalChatLog(Message msg) {
        ChatLog localChatLog = new ChatLog();
        localChatLog.clientMsgID = msg.getClientMsgID();
        localChatLog.serverMsgID = msg.getServerMsgID();
        localChatLog.sendID = msg.getSendID();
        localChatLog.recvID = msg.getRecvID();
        localChatLog.senderNickname = msg.getSenderNickname();
        localChatLog.senderFaceURL = msg.getSenderFaceUrl();
        localChatLog.sessionType = msg.getSessionType();
        localChatLog.msgFrom = msg.getMsgFrom();
        localChatLog.contentType = msg.getContentType();
        localChatLog.content = msg.getContent();
        localChatLog.isRead = msg.isRead();
        localChatLog.status = msg.getStatus();
        localChatLog.seq = (long) msg.getSeq();
        localChatLog.sendTime = msg.getSendTime();
        localChatLog.createTime = msg.getCreateTime();
        localChatLog.attachedInfo = msg.getAttachedInfo();
        localChatLog.ex = msg.getEx().toString();
        localChatLog.isReact = msg.isReact();
        localChatLog.isExternalExtensions = msg.isExternalExtensions();
        return localChatLog;
    }

    public static LocalErrChatLog convertToLocalErrChatLog(ChatLog msg) {
        LocalErrChatLog localErrChatLog = new LocalErrChatLog();
        localErrChatLog.seq = msg.seq;
        localErrChatLog.clientMsgID = msg.clientMsgID;
        localErrChatLog.serverMsgID = msg.serverMsgID;
        localErrChatLog.sendID = msg.sendID;
        localErrChatLog.recvID = msg.recvID;
        localErrChatLog.senderPlatformID = msg.senderPlatformID;
        localErrChatLog.senderNickname = msg.senderNickname;
        localErrChatLog.senderFaceURL = msg.senderFaceURL;
        localErrChatLog.sessionType = msg.sessionType;
        localErrChatLog.msgFrom = msg.msgFrom;
        localErrChatLog.contentType = msg.contentType;
        localErrChatLog.content = msg.content;
        localErrChatLog.isRead = msg.isRead;
        localErrChatLog.status = msg.status;
        localErrChatLog.sendTime = msg.sendTime;
        localErrChatLog.createTime = msg.createTime;
        localErrChatLog.attachedInfo = msg.attachedInfo;
        localErrChatLog.ex = msg.ex;
        return localErrChatLog;
    }

    public static LocalErrChatLog convertToLocalErrChatLog(Message msg) {
        LocalErrChatLog localErrChatLog = new LocalErrChatLog();
        localErrChatLog.seq = msg.getSeq();
        localErrChatLog.clientMsgID = msg.getClientMsgID();
        localErrChatLog.serverMsgID = msg.getServerMsgID();
        localErrChatLog.sendID = msg.getSendID();
        localErrChatLog.recvID = msg.getRecvID();
        localErrChatLog.senderNickname = msg.getSenderNickname();
        localErrChatLog.senderFaceURL = msg.getSenderFaceUrl();
        localErrChatLog.sessionType = msg.getSessionType();
        localErrChatLog.msgFrom = msg.getMsgFrom();
        localErrChatLog.contentType = msg.getContentType();
        localErrChatLog.content = msg.getContent();
        localErrChatLog.isRead = msg.isRead();
        localErrChatLog.status = msg.getStatus();
        localErrChatLog.sendTime = msg.getSendTime();
        localErrChatLog.createTime = msg.getCreateTime();
        localErrChatLog.attachedInfo = msg.getAttachedInfo();
        localErrChatLog.ex = (String) msg.getEx();
        if (msg.getSessionType() == ConversationType.GROUP_CHAT || msg.getSessionType() == ConversationType.SUPER_GROUP_CHAT) {
            localErrChatLog.recvID = msg.getGroupID();
        }
        return localErrChatLog;
    }

    public static Message convertToMsgStruct(ChatLog localChatLog) {
        Message message = new Message();
        message.setClientMsgID(localChatLog.clientMsgID);
        message.setServerMsgID(localChatLog.serverMsgID);
        message.setSendID(localChatLog.sendID);
        message.setRecvID(localChatLog.recvID);
        message.setSenderNickname(localChatLog.senderNickname);
        message.setSenderFaceUrl(localChatLog.senderFaceURL);
        message.setSessionType(localChatLog.sessionType);
        message.setMsgFrom(localChatLog.msgFrom);
        message.setContentType(localChatLog.contentType);
        message.setContent(localChatLog.content);
        message.setRead(localChatLog.isRead);
        message.setStatus(localChatLog.status);
        message.setSeq((int) localChatLog.seq);
        message.setSendTime(localChatLog.sendTime);
        message.setCreateTime(localChatLog.createTime);
        message.setAttachedInfo(localChatLog.attachedInfo);
        message.setEx(localChatLog.ex);
        message.setReact(localChatLog.isReact);
        message.setExternalExtensions(localChatLog.isExternalExtensions);
        return message;
    }

    public static MsgData convertToMsgData(Message m) {
        var msgDataBuilder = MsgData.newBuilder();
        msgDataBuilder.setSendID(m.getSendID());
        msgDataBuilder.setRecvID(m.getRecvID());
        msgDataBuilder.setGroupID(m.getGroupID());
        msgDataBuilder.setClientMsgID(m.getClientMsgID());
        msgDataBuilder.setServerMsgID(m.getServerMsgID());
        msgDataBuilder.setSenderPlatformID(m.getPlatformID());
        msgDataBuilder.setSenderNickname(m.getSenderNickname());
        msgDataBuilder.setSenderFaceURL(m.getSenderFaceUrl());
        msgDataBuilder.setSessionType(m.getSessionType());
        msgDataBuilder.setMsgFrom(m.getMsgFrom());
        msgDataBuilder.setContentType(m.getContentType());
        msgDataBuilder.setContent(ByteString.copyFrom(m.getContent().getBytes()));
        msgDataBuilder.setSeq(m.getSeq());
        msgDataBuilder.setSendTime(m.getSendTime());
        msgDataBuilder.setCreateTime(m.getCreateTime());
        msgDataBuilder.setStatus(m.getStatus());
        msgDataBuilder.setIsRead(m.isRead());
        msgDataBuilder.setOfflinePushInfo(convertToPbOfflinePushInfo(m.getOfflinePush()));
//        msgDataBuilder.addAllAtUserIDList(m.getAtTextElem().getAtUserList());
        msgDataBuilder.setAttachedInfo(m.getAttachedInfo());
        msgDataBuilder.setEx(m.getEx().toString());
        return msgDataBuilder.build();
    }

    public static io.openim.android.sdk.protos.sdkws.OfflinePushInfo convertToPbOfflinePushInfo(OfflinePushInfo offlinePushInfo) {
        if (offlinePushInfo == null) {
            return null;
        }
        var builder = io.openim.android.sdk.protos.sdkws.OfflinePushInfo.newBuilder();
        builder.setTitle(offlinePushInfo.getTitle());
        builder.setDesc(offlinePushInfo.getDesc());
        builder.setEx(offlinePushInfo.getEx());
        builder.setIOSPushSound(offlinePushInfo.getiOSPushSound());
        builder.setIOSBadgeCount(offlinePushInfo.isiOSBadgeCount());
        return builder.build();
    }

    public static Message convertToMsgStruct(MsgData msg) {
        Message message = new Message();
        message.setSendID(msg.getSendID());
        message.setRecvID(msg.getRecvID());
        message.setGroupID(msg.getGroupID());
        message.setClientMsgID(msg.getClientMsgID());
        message.setServerMsgID(msg.getServerMsgID());
        message.setSenderNickname(msg.getSenderNickname());
        message.setSenderFaceUrl(msg.getSenderFaceURL());
        message.setSessionType(msg.getSessionType());
        message.setMsgFrom(msg.getMsgFrom());
        message.setContentType(msg.getContentType());
        message.setContent(msg.getContent().toString());
        message.setSeq((int) msg.getSeq());
        message.setSendTime(msg.getSendTime());
        message.setCreateTime(msg.getCreateTime());
        message.setStatus(msg.getStatus());
        message.setRead(msg.getIsRead());
        message.setOfflinePush(convertOfflinePushInfo(msg.getOfflinePushInfo()));
        message.setAttachedInfo(msg.getAttachedInfo());
        message.setEx(msg.getEx());
        return message;
    }

    public static LocalUser convertToLocalUser(UserInfo userInfo) {
        var localUser = new LocalUser();
        localUser.userID = userInfo.getUserID();
        localUser.nickname = userInfo.getNickname();
        localUser.faceURL = userInfo.getFaceURL();
        localUser.createTime = userInfo.getCreateTime();
        localUser.appMangerLevel = userInfo.getAppMangerLevel();
        localUser.ex = userInfo.getEx();
        localUser.globalRecvMsgOpt = userInfo.getGlobalRecvMsgOpt();
        return localUser;
    }

    public static io.openim.android.sdk.models.UserInfo convertToUserInfo(LocalUser localUser) {
        var userInfo = new io.openim.android.sdk.models.UserInfo();
        userInfo.setUserID(localUser.userID);
        userInfo.setNickname(localUser.nickname);
        userInfo.setFaceURL(localUser.faceURL);
        userInfo.setEx(localUser.ex);
        userInfo.setGlobalRecvMsgOpt(localUser.globalRecvMsgOpt);
        return userInfo;
    }

    public static String getConversationIDBySessionType(String loginUserID, String sourceID, int sessionType) {
        switch (sessionType) {
            case ConversationType.SINGLE_CHAT:
                String[] l = {loginUserID, sourceID};
                Arrays.sort(l);
                return "si_" + String.join("_", l); // single chat
            case ConversationType.GROUP_CHAT:
                return "g_" + sourceID; // group chat
            case ConversationType.SUPER_GROUP_CHAT:
                return "sg_" + sourceID; // super group chat
            case ConversationType.NOTIFICATION:
                return "sn_" + sourceID + "_" + loginUserID; // server notification chat
            default:
                return "";
        }

    }

    public static String getConversationIDByMsg(Message msg) {
        switch (msg.getSessionType()) {
            case ConversationType.SINGLE_CHAT:
                String[] l = {msg.getSendID(), msg.getRecvID()};
                Arrays.sort(l);
                return "si_" + String.join("_", l); // single chat
            case ConversationType.GROUP_CHAT:
                return "g_" + msg.getGroupID(); // group chat
            case ConversationType.SUPER_GROUP_CHAT:
                return "sg_" + msg.getGroupID(); // super group chat
            case ConversationType.NOTIFICATION:
                return "sn_" + msg.getSendID() + "_" + msg.getRecvID(); // server notification chat
            default:
                return "";
        }
    }

    public static OfflinePushInfo convertOfflinePushInfo(io.openim.android.sdk.protos.sdkws.OfflinePushInfo from) {
        OfflinePushInfo offlinePushInfo = new OfflinePushInfo();
        return offlinePushInfo;
    }

}
