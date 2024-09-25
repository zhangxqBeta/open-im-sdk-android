package io.openim.android.sdk.conversation;

import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.utils.JsonUtil;
import java.util.List;
import java.util.Map;

public class MessageController {

    public static Exception batchUpdateMessageList(Map<String, List<ChatLog>> updateMsg) {
        if (updateMsg == null || updateMsg.isEmpty()) {
            return null;
        }

        for (var entry : updateMsg.entrySet()) {
            var conversationID = entry.getKey();
            var messages = entry.getValue();
            var conversation = ChatDbManager.getInstance().getImDatabase().localConversationDao().getConversation(conversationID);
            if (conversation == null) {
                continue;
            }

            var latestMsg = JsonUtil.toObj(conversation.latestMsg, Message.class);
            for (var v : messages) {
                var v1 = new ChatLog();
                v1.clientMsgID = v.clientMsgID;
                v1.seq = v.seq;
                v1.status = v.status;
                v1.recvID = v.recvID;
                v1.sessionType = v.sessionType;
                v1.serverMsgID = v.serverMsgID;
                v1.sendTime = v.sendTime;
                v1.conversationID = conversationID;
                int affectedRows = ChatDbManager.getInstance().getImDatabase().chatLogDao().update(v1);
                if (affectedRows == 0) {
                    return new Exception("batchUpdateMessageList failed");
                }

                if (latestMsg.getClientMsgID() == v.clientMsgID) {
                    latestMsg.setServerMsgID(v.serverMsgID);
                    latestMsg.setSeq((int) v.seq);
                    latestMsg.setSendTime(v.sendTime);
                    conversation.latestMsg = JsonUtil.toString(latestMsg);
                    Trigger.triggerCmdUpdateConversation(new UpdateConNode(conversation.conversationID, Constants.ADD_CON_OR_UP_LAT_MSG, conversation),
                        OpenIMClient.getInstance().getConversationCh());
                }
            }

        }
        return null;
    }

    public static Exception batchInsertMessageList(Map<String, List<ChatLog>> insertMsg) {
        if (insertMsg == null || insertMsg.isEmpty()) {
            return null;
        }

        for (var entry : insertMsg.entrySet()) {
            var conversationID = entry.getKey();
            var messages = entry.getValue();
            if (messages.size() == 0) {
                continue;
            }
            messages.forEach((message) -> {
                message.conversationID = conversationID;
            });
            ChatDbManager.getInstance().getImDatabase().chatLogDao().bulkInsert(messages);
        }

        return null;
    }
}
