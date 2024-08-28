package io.openim.android.sdk.conversation;

import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.common.DeleteConNode;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.common.UpdateMessageNode;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.models.ConversationInfo;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.protos.sdkws.ConversationUpdateTips;
import io.openim.android.sdk.protos.sdkws.MsgData;
import io.openim.android.sdk.utils.ConvertUtil;
import io.openim.android.sdk.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kotlin.NotImplementedError;

public class ConversationNotification {

    public static void doConversationChangedNotification(MsgData msg) {
        var tips = ConversationUpdateTips.newBuilder().build();
        //unmarshal, todo: check parsing
        try {
            JsonUtil.parseNotificationElem(msg.getContent().toStringUtf8(), ConversationUpdateTips.class);
        } catch (Exception e) {
            return;
        }
        Sync.syncConversations(tips.getConversationIDListList());
    }

    public static void doUpdateMessage(Cmd2Value c2v) {
        UpdateMessageNode node = (UpdateMessageNode) c2v.value;
        switch (node.action) {
            case Constants.UPDATE_MSG_FACE_URL_AND_NICKNAME:
                //golang impl: case constant.UpdateMsgFaceUrlAndNickName
                //no op for now.
                break;
        }
    }

    public static void doDeleteConversation(Cmd2Value c2v) {
        DeleteConNode node = (DeleteConNode) c2v.value;
        var imDatabase = ChatDbManager.getInstance().getImDatabase();
        imDatabase.localChatLogDao()
            .updateMessageStatusBySourceID(node.sourceID, Constants.MSG_STATUS_HAS_DELETED, node.sessionType);
        imDatabase.localConversationDao().resetConversation(node.conversationID);
        doUpdateConversation(new Cmd2Value(new UpdateConNode("", Constants.TOTAL_UNREAD_MESSAGE_CHANGED, "")));
    }

    public static void doUpdateConversation(Cmd2Value c2v) {
        UpdateConNode node = (UpdateConNode) c2v.value;
        var localConversationDao = ChatDbManager.getInstance().getImDatabase().localConversationDao();
        switch (node.action) {
            case Constants.ADD_CON_OR_UP_LAT_MSG:
                //golang impl: model_struct.LocalConversation
                List<ConversationInfo> list = new ArrayList<>();
                LocalConversation lc = (LocalConversation) node.args;
                LocalConversation oc = localConversationDao.getConversation(lc.conversationID);
                //found it
                if (oc != null) {
                    if (lc.latestMsgSendTime >= oc.latestMsgSendTime || getConversationLatestMsgClientID(lc.latestMsg) == getConversationLatestMsgClientID(
                        oc.latestMsg)) {
                        oc.latestMsgSendTime = lc.latestMsgSendTime;
                        oc.latestMsg = lc.latestMsg;
                        int rows = localConversationDao.update(oc);
                        if (rows == 0) {
                            LogcatHelper.logDInDebug("updateConversationLatestMsgModel error");
                        } else {
                            list.add(ConvertUtil.convertToConversationInfo(oc));
                            Conversation.getInstance().getOnConversationListener().onConversationChanged(list);
                        }
                    }

                } else {
                    //not found, insert it
                    var res = localConversationDao.insert(lc);
                    //error
                    if (res == 0) {
                        LogcatHelper.logDInDebug("insert new conversation err");
                    } else {
                        list.add(ConvertUtil.convertToConversationInfo(lc));
                        Conversation.getInstance().getOnConversationListener().onNewConversation(list);
                    }
                }
                break;
            case Constants.UNREAD_COUNT_SET_ZERO:
                try {
                    localConversationDao.updateUnreadCount(node.conID, 0);
                    var totalUnreadCount = ChatDbManager.getInstance().getTotalUnreadMsgCountDB();
                    Conversation.getInstance().getOnConversationListener().onTotalUnreadMessageCountChanged(totalUnreadCount);

                } catch (Exception e) {
                    LogcatHelper.logDInDebug(e.getMessage());
                }
                break;
            case Constants.INCR_UNREAD:
                localConversationDao.incrUnreadCount(node.conID);
                break;
            case Constants.TOTAL_UNREAD_MESSAGE_CHANGED:
                var totalUnreadCount = ChatDbManager.getInstance().getTotalUnreadMsgCountDB();
                Conversation.getInstance().getOnConversationListener().onTotalUnreadMessageCountChanged(totalUnreadCount);
                break;
            case Constants.UPDATE_CON_FACE_URL_AND_NICKNAME:
                //no op
                break;
            case Constants.UPDATE_LATEST_MESSAGE_CHANGE:
                var conversationID = node.conID;
                var l = localConversationDao.getConversation(conversationID);
                var latestMsg = JsonUtil.toObj(l.latestMsg, Message.class);
                latestMsg.setRead(true);
                var newLatestMessage = JsonUtil.toString(latestMsg);
//                l.latestMsgSendTime = latestMsg.getSendTime();
//                l.latestMsg = newLatestMessage;
//                localConversationDao.update(l);
                localConversationDao.updateLatestMsgAndSendTime(node.conID, latestMsg.getSendTime(), newLatestMessage);
                break;
            case Constants.CON_CHANGE:
                String[] conversationIDs = (String[]) node.args;
                var converstions = localConversationDao.getMultipleConversationDB(conversationIDs);
                var newCList = new ArrayList<ConversationInfo>();
                for (var v : converstions) {
                    if (v.latestMsgSendTime != 0) {
                        newCList.add(ConvertUtil.convertToConversationInfo(v));
                    }
                }
                Conversation.getInstance().getOnConversationListener().onConversationChanged(newCList);
                break;
            case Constants.NEW_CON:
                String[] cidList = (String[]) node.args;
                var cLists = localConversationDao.getMultipleConversationDB(cidList);
                if (cLists != null && !cLists.isEmpty()) {
                    List<ConversationInfo> infos = cLists.stream().map(ConvertUtil::convertToConversationInfo).collect(Collectors.toList());
                    Conversation.getInstance().getOnConversationListener().onConversationChanged(infos);
                }
                break;
            case Constants.CON_CHANGE_DIRECT:
                ArrayList<LocalConversation> lcList = (ArrayList<LocalConversation>) node.args;
                List<ConversationInfo> infos = lcList.stream().map(ConvertUtil::convertToConversationInfo).collect(Collectors.toList());
                Conversation.getInstance().getOnConversationListener().onConversationChanged(infos);
                break;
            case Constants.NEW_CON_DIRECT:
                ArrayList<LocalConversation> newLcList = (ArrayList<LocalConversation>) node.args;
                List<ConversationInfo> newInfos = newLcList.stream().map(ConvertUtil::convertToConversationInfo).collect(Collectors.toList());
                Conversation.getInstance().getOnConversationListener().onNewConversation(newInfos);
                break;
            case Constants.CONVERSATION_LATEST_MSG_HAS_READ:
                Map<String, List<String>> hasReadMsgList = (Map<String, List<String>>) node.args;
                var result = new ArrayList<ConversationInfo>();

                for (var entry : hasReadMsgList.entrySet()) {
                    var conversationID2 = entry.getKey();
                    var msgIDList = entry.getValue();
                    //naming: due to java switch-case issue, append "2" to the end to align with golang code
                    var lc2 = new LocalConversation();
                    try {
                        LocalConversation localConversation = localConversationDao.getConversation(conversationID2);
                        if (localConversation == null) {
                            continue;
                        }
                        var latestMsg2 = JsonUtil.toObj(localConversation.latestMsg, Message.class);
                        if (msgIDList.contains(latestMsg2.getClientMsgID())) {
                            latestMsg2.setRead(true);
                            lc2.conversationID = conversationID2;
                            lc2.latestMsg = JsonUtil.toString(latestMsg2);
                            localConversation.latestMsg = JsonUtil.toString(latestMsg2);
                            int affected = localConversationDao.update(lc2);
                            if (affected == 0) {
                                continue;
                            } else {
                                result.add(ConvertUtil.convertToConversationInfo(localConversation));
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }

                if (result != null && !result.isEmpty()) {
                    Conversation.getInstance().getOnConversationListener().onNewConversation(result);
                }

                break;
            case Constants.SYNC_CONVERSATION:
                //no op
                break;

        }
    }

    public static String getConversationLatestMsgClientID(String latestMsg) {
        var msgStruct = JsonUtil.toObj(latestMsg, Message.class);
        return msgStruct.getClientMsgID();
    }

}
