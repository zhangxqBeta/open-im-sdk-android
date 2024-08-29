package io.openim.android.sdk.conversation;

import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.http.ApiClient;
import io.openim.android.sdk.http.ServerApiRouter;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.protos.msg.ClearConversationsMsgReq;
import io.openim.android.sdk.protos.sdkws.ClearConversationTips;
import io.openim.android.sdk.protos.sdkws.DeleteMsgsTips;
import io.openim.android.sdk.protos.sdkws.MsgData;
import io.openim.android.sdk.sdkdto.ClearConversationTipsPojo;
import io.openim.android.sdk.sdkdto.DeleteMsgsTipsPojo;
import io.openim.android.sdk.utils.ConvertUtil;
import io.openim.android.sdk.utils.JsonUtil;
import java.util.Arrays;
import java.util.function.Function;

public class Delete {

    public static void doClearConversations(MsgData msg) {
        var tips = JsonUtil.parseNotificationElem(msg.getContent().toStringUtf8(), ClearConversationTipsPojo.class);
        for (var v : tips.getConversationIDsList()) {
            var err = clearConversationAndDeleteAllMsg(v, false, null);
            if (err != null) {
                LogcatHelper.logDInDebug("doClearConversations err: " + err);
            }
        }
        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.CON_CHANGE, tips.getConversationIDsList())));
        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED)));

    }

    public static void doDeleteMsgs(MsgData msg) {
        var tips = JsonUtil.parseNotificationElem(msg.getContent().toStringUtf8(), DeleteMsgsTipsPojo.class);
        for (var v : tips.getSeqsList()) {
            try {
                var localMsg = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessageBySeq(tips.getConversationID(), v);
            } catch (Exception e) {
                continue;
            }
            //golang impl, weird, skip for now
            //	var s sdk_struct.MsgStruct
            //		copier.Copy(&s, msg)
            //		err = c.msgConvert(&s)
            //		if err != nil {
            //			////log.ZError(ctx, "parsing data error", err, "msg", msg)
            //		}
            Delete.deleteMessageFromLocal(tips.getConversationID(), msg.getClientMsgID());
        }
    }

    public static Exception deleteMessageFromLocal(String conversationID, String clientMsgID) {
        try {

            var localChatLogDao = ChatDbManager.getInstance().getImDatabase().chatLogDao();
            var localConversationDao = ChatDbManager.getInstance().getImDatabase().localConversationDao();
            var s = localChatLogDao.getMessage(conversationID, clientMsgID);
            localChatLogDao.deleteConversationMsgs(conversationID, new String[]{clientMsgID});
            if (!s.isRead && s.sendID != IMConfig.getInstance().userID) {
                localConversationDao.decrConversationUnreadCount(conversationID, 1);
                ConversationNotification.doUpdateConversation(
                    new Cmd2Value(new UpdateConNode(conversationID, Constants.CON_CHANGE, new String[]{conversationID})));
                ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED)));
            }

            var conversation = localConversationDao.getConversation(conversationID);
            var latestMsg = JsonUtil.toObj(conversation.latestMsg, Message.class);
            if (latestMsg.getClientMsgID() == clientMsgID) {
                //golang isReverse=false: Desc
                var msgs = localChatLogDao.getMessageListNoTimeDESC(conversationID, 1);
                var latestMsgSendTime = latestMsg.getSendTime();
                var latestMsgStr = "";

                if (msgs.size() > 0) {
                    latestMsg = ConvertUtil.convertToMsgStruct(msgs.get(0));
                    var err = Conversation.msgConvert(latestMsg);
                    if (err != null) {
                        LogcatHelper.logDInDebug(err.getMessage());
                    }
                    latestMsgStr = JsonUtil.toString(latestMsg);
                    latestMsgSendTime = latestMsg.getSendTime();
                }
                ChatDbManager.getInstance().getImDatabase().localConversationDao().updateLatestMsgAndSendTime(conversationID, latestMsgSendTime, latestMsgStr);
                ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.CON_CHANGE, new String[]{conversationID})));
            }

            Conversation.getInstance().getMsgListener().onMsgDeleted(ConvertUtil.convertToMsgStruct(s));

            return null;
        } catch (Exception e) {
            return e;
        }
    }

    public static Exception clearConversationAndDeleteAllMsg(String conversationID, boolean markDelete, Function<String, Exception> f) {
        var err = ReadDrawing.getConversationMaxSeqAndSetHasRead(conversationID);
        if (err != null) {
            return err;
        }

        var localChatLogDao = ChatDbManager.getInstance().getImDatabase().chatLogDao();
        if (markDelete) {
            localChatLogDao.markDeleteConversationAllMessages(conversationID);
        } else {
            localChatLogDao.deleteConversationAllMessages(conversationID);
        }

        if (err != null) {
            return err;
        }

        err = f.apply(conversationID);
        if (err != null) {
            return err;
        }
        return null;
    }

    public static Exception clearConversationFromLocalAndSvr(String conversationID, Function<String, Exception> f) {
        var c = ChatDbManager.getInstance().getImDatabase().localConversationDao().getConversation(conversationID);
        if (c == null) {
            return new Exception(String.format("conversation with id: %s not found", conversationID));
        }

        // Use conversationID to remove conversations and messages from the server first
        var err = clearConversationMsgFromSvr(conversationID);
        if (err != null) {
            return err;
        }

        err = clearConversationAndDeleteAllMsg(conversationID, false, f);
        if (err != null) {
            return err;
        }

        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.CON_CHANGE, new String[]{conversationID})));
        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED)));
        return null;
    }

    public static Exception clearConversationMsgFromSvr(String conversationID) {
        var apiReq = ClearConversationsMsgReq.newBuilder().setUserID(IMConfig.getInstance().userID).addAllConversationIDs(Arrays.asList(conversationID))
            .build();
        var jsonReq = ConvertUtil.protobufToJsonStr(apiReq);
        var returnWithErr = ApiClient.apiPost(ServerApiRouter.ClearConversationMsgRouter, jsonReq, null);
        return returnWithErr.getError();
    }
}
