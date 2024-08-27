package io.openim.android.sdk.conversation;


import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.internal.log.LogcatHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sync {

    public static Exception syncConversations(List<String> conversationIDs) {
        var returnWithErr = Conversation.getInstance().getServerConversationsByIDs(conversationIDs);
        if (returnWithErr.hasError()) {
            return returnWithErr.getError();
        }

        var conversationsOnServer = returnWithErr.getPayload();

        return syncConversationsAndTriggerCallback(conversationsOnServer);
    }

    public static Exception syncConversationsAndTriggerCallback(List<LocalConversation> conversationsOnServer) {
        var conversationsOnLocal = ChatDbManager.getInstance().getImDatabase().localConversationDao().getAllConversations();
        var err = Conversation.getInstance().getConversationSyncer()
            .sync(conversationsOnServer, conversationsOnLocal, (Integer state, LocalConversation server, LocalConversation local) -> {
                if (state == Syncer.UpdateState || state == Syncer.InsertState) {
                    ConversationNotification.doUpdateConversation(
                        new Cmd2Value(new UpdateConNode(server.conversationID, Constants.CON_CHANGE, new String[]{server.conversationID})));
                }
                return null;
            }, true);

        if (err != null) {
            return err;
        }
        return null;
    }

    public static Exception syncAllConversations() {
        var returnWithErr = Conversation.getServerConversationList();
        if (returnWithErr.hasError()) {
            return returnWithErr.getError();
        }

        var conversationsOnServer = returnWithErr.getPayload();

        return Sync.syncConversationsAndTriggerCallback(conversationsOnServer);
    }

    public static Exception syncAllConversationHasReadSeqs() {
        var returnWithErr = Conversation.getInstance().getServerHasReadAndMaxSeqs(new ArrayList<>());
        if (returnWithErr.hasError()) {
            return returnWithErr.getError();
        }

        var seqs = returnWithErr.getPayload();
        if (seqs != null && seqs.size() == 0) {
            return null;
        }

        List<String> conversationChangedIDs = new ArrayList<>();
        List<String> conversationIDsNeedSync = new ArrayList<>();
        var localConversationDao = ChatDbManager.getInstance().getImDatabase().localConversationDao();

        var conversationsOnLocal = localConversationDao.getAllConversations();
        Map<String, LocalConversation> conversationsOnLocalMap = conversationsOnLocal.stream()
            .collect(Collectors.toMap(LocalConversation::getConversationID, Function.identity()));
        for (var entry : seqs.entrySet()) {
            var conversationID = entry.getKey();
            var v = entry.getValue();
            int unreadCount = 0;
            Conversation.getInstance().getMaxSeqRecorder().set(conversationID, v.getMaxSeq());
            if (v.getMaxSeq() - v.getHasReadSeq() < 0) {
                unreadCount = 0;
            } else {
                unreadCount = (int) (v.getMaxSeq() - v.getHasReadSeq());
            }

            if (conversationsOnLocalMap.containsKey(conversationID)) {
                var conversation = conversationsOnLocalMap.get(conversationID);
                if (conversation.unreadCount != unreadCount || conversation.hasReadSeq != v.getHasReadSeq()) {
                    try {
                        localConversationDao
                            .updateUnreadCountAndHasReadSeq(conversationID, unreadCount, v.getHasReadSeq());
                    } catch (Exception e) {
                        LogcatHelper.logDInDebug("updateUnreadCountAndHasReadSeq err: " + e);
                        continue;
                    }

                    conversationChangedIDs.add(conversationID);
                }

            } else {
                conversationIDsNeedSync.add(conversationID);
            }
        }

        if (conversationIDsNeedSync.size() > 0) {
            var payloadWithErr = Conversation.getInstance().getServerConversationsByIDs(conversationIDsNeedSync);
            if (payloadWithErr.hasError()) {
                return payloadWithErr.getError();
            }

            //batchAddFaceURLAndName; skip
            var conversationsOnServer = payloadWithErr.getPayload();

            for (var conversation : conversationsOnServer) {
                var unreadCount = 0;
                if (!seqs.containsKey(conversation.conversationID)) {
                    continue;
                }

                var v = seqs.get(conversation.conversationID);
                if (v.getMaxSeq() - v.getHasReadSeq() < 0) {
                    unreadCount = 0;
                } else {
                    unreadCount = (int) (v.getMaxSeq() - v.getHasReadSeq());
                }
                conversation.unreadCount = unreadCount;
                conversation.hasReadSeq = v.getHasReadSeq();

            }

            try {
                localConversationDao.batchInsert(conversationsOnServer);
            } catch (Exception e) {
                LogcatHelper.logDInDebug("batchInsert err: " + e);
            }

        }

        if (conversationChangedIDs.size() > 0) {
            Trigger.triggerCmdUpdateConversation(new UpdateConNode(Constants.CON_CHANGE, conversationChangedIDs),
                OpenIMClient.getInstance().getConversationCh());
            Trigger.triggerCmdUpdateConversation(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED), OpenIMClient.getInstance().getConversationCh());
        }
        return null;
    }
}
