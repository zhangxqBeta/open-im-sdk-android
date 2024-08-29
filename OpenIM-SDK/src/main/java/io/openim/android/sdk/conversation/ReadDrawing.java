package io.openim.android.sdk.conversation;

import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.common.SdkException;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.ConversationType;
import io.openim.android.sdk.generics.ReturnWithErr;
import io.openim.android.sdk.http.ApiClient;
import io.openim.android.sdk.http.ServerApiRouter;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.models.AttachedInfoElem;
import io.openim.android.sdk.models.C2CReadReceiptInfo;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.protos.msg.MarkConversationAsReadReq;
import io.openim.android.sdk.protos.msg.SetConversationHasReadSeqReq;
import io.openim.android.sdk.protos.sdkws.MarkAsReadTips;
import io.openim.android.sdk.protos.sdkws.MsgData;
import io.openim.android.sdk.sdkdto.MarkAsReadTipsPojo;
import io.openim.android.sdk.utils.ConvertUtil;
import io.openim.android.sdk.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;

public class ReadDrawing {

    public static Exception getConversationMaxSeqAndSetHasRead(String conversationID) {
        var imDatabase = ChatDbManager.getInstance().getImDatabase();
        long maxSeq = imDatabase.chatLogDao().getConversationNormalMsgSeq(conversationID);
        if (maxSeq == 0) {
            return null;
        }
        var err = setConversationHasReadSeq(conversationID, maxSeq);
        if (err != null) {
            return err;
        }

        try {
            imDatabase.localConversationDao().updateHasReadSeq(conversationID, maxSeq);
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    public static Exception setConversationHasReadSeq(String conversationID, long hasReadSeq) {
        var req = SetConversationHasReadSeqReq.newBuilder().setUserID(IMConfig.getInstance().userID).setConversationID(conversationID).setHasReadSeq(hasReadSeq)
            .build();

        var jsonReq = ConvertUtil.protobufToJsonStr(req);
        var returnWithErr = ApiClient.apiPost(ServerApiRouter.SetConversationHasReadSeq, jsonReq, null);
        return returnWithErr.getError();
    }

    public static void doReadDrawing(MsgData msg) {
        var tips = JsonUtil.parseNotificationElem(msg.getContent().toStringUtf8(), MarkAsReadTipsPojo.class);
        var imDatabase = ChatDbManager.getInstance().getImDatabase();
        var conversation = imDatabase.localConversationDao().getConversation(tips.getConversationID());
        if (tips.getMarkAsReadUserID() != IMConfig.getInstance().userID) {
            if (tips.getSeqsList().size() == 0) {
                return;
            }
            //GetMessagesBySeqs
            var messages = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessagesBySeqs(tips.getConversationID(), tips.getSeqsList());
            if (conversation.conversationType == ConversationType.SINGLE_CHAT) {
                var latestMsg = JsonUtil.toObj(conversation.latestMsg, Message.class);

                List<String> successMsgIDs = new ArrayList<>();
                for (var message : messages) {
                    var attachInfo = JsonUtil.toObj(message.attachedInfo, AttachedInfoElem.class);
                    attachInfo.setHasReadTime(msg.getSendTime());
                    message.attachedInfo = JsonUtil.toString(attachInfo);
                    message.isRead = true;
                    //different
                    //message.conversationID = tips.getConversationID();
                    imDatabase.chatLogDao().update(message);

                    if (latestMsg.getClientMsgID() == message.clientMsgID) {
                        latestMsg.setRead(message.isRead);
                        conversation.latestMsg = JsonUtil.toString(latestMsg);
                        Trigger.triggerCmdUpdateConversation(new UpdateConNode(conversation.conversationID, Constants.ADD_CON_OR_UP_LAT_MSG, conversation),
                            OpenIMClient.getInstance().getConversationCh());
                    }

                    successMsgIDs.add(message.clientMsgID);
                }
                var messageReceiptResp = new ArrayList<C2CReadReceiptInfo>();
                messageReceiptResp.add(new C2CReadReceiptInfo(tips.getMarkAsReadUserID(), successMsgIDs, conversation.conversationType, msg.getSendTime()));
                Conversation.getInstance().getMsgListener().onRecvC2CReadReceipt(messageReceiptResp);
            }
        } else {
            doUnreadCount(conversation, tips.getHasReadSeq(), tips.getSeqsList());
        }
    }


    public static ReturnWithErr<Long> markConversationMessageAsReadDB(String conversationID, List<String> msgIDs) {
        long rowsAffected = 0;
        var msgs = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessagesByIDs(conversationID, msgIDs, IMConfig.getInstance().userID);
        for (var msg : msgs) {
            var attachedInfo = JsonUtil.toObj(msg.attachedInfo, io.openim.android.sdk.models.AttachedInfoElem.class);
            if (attachedInfo == null) {
                continue;
            }
            attachedInfo.setHasReadTime(System.currentTimeMillis());
            msg.isRead = true;
            msg.attachedInfo = JsonUtil.toString(attachedInfo);
            msg.conversationID = conversationID;
            var affected = ChatDbManager.getInstance().getImDatabase().chatLogDao().update(msg);
            if (affected == 0) {
                //error
            } else {
                rowsAffected++;
            }
        }

        return new ReturnWithErr<>(rowsAffected);
    }

    public static SdkException markConversationMessageAsRead(String conversationID) {
        try {

            var imDatabase = ChatDbManager.getInstance().getImDatabase();

            var conversation = imDatabase.localConversationDao().getConversation(conversationID);
            if (conversation.unreadCount == 0) {
                return SdkException.ErrUnreadCount;
            }

            var peerUserMaxSeq = imDatabase.chatLogDao()
                .getConversationPeerNormalMsgSeq(conversationID, IMConfig.getInstance().userID);

            var maxSeq = imDatabase.chatLogDao().getConversationNormalMsgSeq(conversationID);

            switch (conversation.conversationType) {
                case ConversationType.SINGLE_CHAT:
                    var msgs = imDatabase.chatLogDao().getUnreadMessage(conversationID, IMConfig.getInstance().userID);
                    var msgIDs = new ArrayList<String>();
                    var seqs = new ArrayList<Long>();
                    getAsReadMsgMapAndList(msgs, msgIDs, seqs);
                    if (seqs.size() == 0) {
                        conversation.unreadCount = 0;
                    }

                    var err = markConversationAsReadSvr(conversationID, maxSeq, seqs);
                    if (err != null) {
                        return SdkException.parseException(err);
                    }

                    var returnWithErr = markConversationMessageAsReadDB(conversationID, msgIDs);
                    if (returnWithErr.hasError()) {
                        return SdkException.parseException(returnWithErr.getError());
                    }

                    break;
                case ConversationType.SUPER_GROUP_CHAT:
                case ConversationType.NOTIFICATION:
                    break;

            }

            ChatDbManager.getInstance().getImDatabase().localConversationDao().updateUnreadCount(conversationID, 0);
            unreadChangeTrigger(conversationID, peerUserMaxSeq == maxSeq);
            return null;
        } catch (Exception e) {
            return SdkException.parseException(e);
        }
    }

    public static void unreadChangeTrigger(String conversationID, boolean latestMsgIsRead) {
        if (latestMsgIsRead) {
            ConversationNotification.doUpdateConversation(
                new Cmd2Value(new UpdateConNode(conversationID, Constants.UPDATE_LATEST_MESSAGE_CHANGE, new String[]{conversationID})));
        }
        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(conversationID, Constants.CON_CHANGE, new String[]{conversationID})));
        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED)));
    }

    public static Exception markConversationAsReadSvr(String conversationID, long hasReadSeq, List<Long> seqs) {
        var req = MarkConversationAsReadReq.newBuilder().setUserID(IMConfig.getInstance().userID).setConversationID(conversationID).setHasReadSeq(hasReadSeq)
            .build();
        var jsonReq = ConvertUtil.protobufToJsonStr(req);
        var returnWithErr = ApiClient.apiPost(ServerApiRouter.MarkConversationAsRead, jsonReq, null);
        return returnWithErr.getError();
    }

    public static void getAsReadMsgMapAndList(List<ChatLog> msgs, List<String> asReadMsgIDs, List<Long> seqs) {
        for (var msg : msgs) {
            if (!msg.isRead && msg.sendID != IMConfig.getInstance().userID) {
                if (msg.seq == 0) {
                    LogcatHelper.logDInDebug("exception, seq is 0");
                } else {
                    asReadMsgIDs.add(msg.clientMsgID);
                    seqs.add(msg.seq);
                }
            } else {
                LogcatHelper.logDInDebug("msg can't marked as read");
            }
        }
    }

    public static void doUnreadCount(LocalConversation conversation, long hasReadSeq, List<Long> seqs) {
        if (conversation.conversationType == ConversationType.SINGLE_CHAT) {
            if (!seqs.isEmpty()) {
                ChatDbManager.getInstance().getImDatabase().chatLogDao()
                    .markConversationMessageAsReadBySeqs(conversation.conversationID, seqs, IMConfig.getInstance().userID);
            } else {
                LogcatHelper.logDInDebug("seqs is empty");
            }

            if (hasReadSeq > conversation.hasReadSeq) {
                var decrUnreadCount = hasReadSeq - conversation.hasReadSeq;
                var localConversationDao = ChatDbManager.getInstance().getImDatabase().localConversationDao();
                localConversationDao.decrConversationUnreadCount(conversation.conversationID, decrUnreadCount);
                localConversationDao.updateHasReadSeq(conversation.conversationID, hasReadSeq);
            }

            var latestMsg = JsonUtil.toObj(conversation.latestMsg, Message.class);

            if (!latestMsg.isRead() && seqs.contains(latestMsg.getSeq())) {
                latestMsg.setRead(true);
                conversation.latestMsg = JsonUtil.toString(latestMsg);
                Trigger
                    .triggerCmdUpdateConversation(new UpdateConNode(conversation.conversationID, Constants.ADD_CON_OR_UP_LAT_MSG, conversation),
                        OpenIMClient.getInstance()
                            .getConversationCh());
            }

        } else {
            ChatDbManager.getInstance().getImDatabase().localConversationDao().updateUnreadCount(conversation.conversationID, 0);
        }

        ConversationNotification.doUpdateConversation(
            new Cmd2Value(new UpdateConNode(conversation.conversationID, Constants.CON_CHANGE, new String[]{conversation.conversationID})));
        ConversationNotification.doUpdateConversation(new Cmd2Value(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED)));
    }
}
