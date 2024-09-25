package io.openim.android.sdk.handler;

import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.connection.ConnectionManager;
import io.openim.android.sdk.conversation.Trigger;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.enums.Cmd;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.ReqIdentifier;
import io.openim.android.sdk.generics.ReturnWithErr;
import io.openim.android.sdk.generics.ReturnWithSdkErr;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.models.CmdMaxSeqToMsgSync;
import io.openim.android.sdk.protos.sdkws.GetMaxSeqReq;
import io.openim.android.sdk.protos.sdkws.GetMaxSeqResp;
import io.openim.android.sdk.protos.sdkws.MsgData;
import io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq;
import io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp;
import io.openim.android.sdk.protos.sdkws.PullMsgs;
import io.openim.android.sdk.protos.sdkws.PushMessages;
import io.openim.android.sdk.protos.sdkws.SeqRange;
import io.openim.android.sdk.sdkdto.CmdNewMsgComeToConversation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MsgSyncer {

    public boolean reinstalled;
    public Map<String, Long> syncedMaxSeqs = new HashMap<>();

    public static final int CONNECT_PULL_NUMS = 1;
    public static final int DEFAULT_PULL_NUMS = 10;
    public static final int SPLIT_PULL_MSG_NUM = 100;

    public static MsgSyncer getInstance() {
        return MsgSyncer.SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static MsgSyncer instance = new MsgSyncer();
    }

    private MsgSyncer() {
    }


    public BlockingQueue<Cmd2Value> getPushMsgAndMaxSeqCh() {
        return OpenIMClient.getInstance().getPushMsgAndMaxSeqCh();
    }

    public void doListener() {
        while (true) {
            try {
                Cmd2Value cmd = getPushMsgAndMaxSeqCh().take();
                LogcatHelper.logDInDebug(String.format("websocket pushMsgAndMaxSeqCh take cmd: %s", cmd));
                handlePushMsgAndEvent(cmd);
            } catch (Exception e) {
                LogcatHelper.logDInDebug(String.format("websocket pushMsgAndMaxSeqCh listener exception: %s", e));
            }
        }
    }

    private void handlePushMsgAndEvent(Cmd2Value cmd2Value) {
        LogcatHelper.logDInDebug(String.format("websocket handlePushMsgAndEvent cmd type : %s", cmd2Value.cmd));
        switch (cmd2Value.cmd) {
            //golang impl: handlePushMsgAndEvent
            case Cmd.CMD_CONN_SUCCESS:
                MsgSyncer.getInstance().doConnected();
                return;
            case Cmd.CMD_MAX_SEQ:
                CmdMaxSeqToMsgSync value = (CmdMaxSeqToMsgSync) cmd2Value.value;
                MsgSyncer.getInstance().compareSeqsAndBatchSync(value.conversationMaxSeqOnSvr, MsgSyncer.CONNECT_PULL_NUMS);
                return;
            case Cmd.CMD_PUSH_MSG:
                MsgSyncer.getInstance().doPushMsg((PushMessages) cmd2Value.value);
                return;
        }
    }

    public void loadSeq() {
        var imDatabase = ChatDbManager.getInstance().getImDatabase();
        var conversationIDList = imDatabase.localConversationDao().getAllConversationIDList();
        if (conversationIDList == null || conversationIDList.size() == 0) {
            reinstalled = true;
        }

        for (var v : conversationIDList) {
            long maxSyncedSeq = imDatabase.chatLogDao().getConversationNormalMsgSeq(v);
            syncedMaxSeqs.put(v, maxSyncedSeq);
        }
        var notificationSeqs = imDatabase.notificationSeqsDao().getNotificationAllSeqs();
        for (var notificationSeq : notificationSeqs) {
            syncedMaxSeqs.put(notificationSeq.conversationID, notificationSeq.seq);
        }
    }

    public void doConnected() {
        var conversationCh = OpenIMClient.getInstance().getConversationCh();
        Trigger.triggerCmdNotification(new CmdNewMsgComeToConversation(Constants.MSG_SYNC_BEGIN), conversationCh);
        var returnWithSdkErr = ConnectionManager.getInstance().getConnection()
            .sendReqWaitResp(ReqIdentifier.GET_NEWEST_SEQ, GetMaxSeqReq.newBuilder().setUserID(IMConfig.getInstance().userID).build(), GetMaxSeqResp.parser());
        if (returnWithSdkErr.hasError()) {
            Trigger.triggerCmdNotification(new CmdNewMsgComeToConversation(Constants.MSG_SYNC_FAILED), conversationCh);
            return;
        } else {

        }

        var resp = returnWithSdkErr.getPayload();

        compareSeqsAndBatchSync(resp.getMaxSeqsMap(), CONNECT_PULL_NUMS);
        Trigger.triggerCmdNotification(new CmdNewMsgComeToConversation(Constants.MSG_SYNC_END), conversationCh);
    }

    public void doPushMsg(PushMessages push) {
        pushTriggerAndSync(push.getMsgsMap());
        pushTriggerAndSync(push.getNotificationMsgsMap());
    }

    //golang impl: triggerFunc func(ctx context.Context, msgs map[string]*sdkws.PullMsgs) error
    //triggerFunc will be always m.triggerConversation
    public void pushTriggerAndSync(Map<String, PullMsgs> pullMsgs) {
        LogcatHelper.logDInDebug(String.format("websocket pushTriggerAndSync pullMsgs size: %d", pullMsgs.size()));
        if (pullMsgs == null || pullMsgs.size() == 0) {
            return;
        }

        var needSyncSeqMap = new HashMap<String, long[]>();
        long lastSeq = 0;
        List<MsgData> storageMsgs = new ArrayList<>();
        for (var entry : pullMsgs.entrySet()) {
            var conversationID = entry.getKey();
            var msgs = entry.getValue();
            for (var msg : msgs.getMsgsList()) {
                if (msg.getSeq() == 0) {
                    var value = new HashMap<String, PullMsgs>();
                    value.put(conversationID, PullMsgs.newBuilder().addMsgs(msg).build());
                    triggerConversation(value);
                    continue;
                }
                lastSeq = msg.getSeq();
                storageMsgs.add(msg);
            }

            Long maxSeqVal = syncedMaxSeqs.get(conversationID);
            long maxSeq = maxSeqVal == null ? 0 : maxSeqVal;
            if (lastSeq == maxSeq + storageMsgs.size() && lastSeq != 0) {
                var value = new HashMap<String, PullMsgs>();
                value.put(conversationID, PullMsgs.newBuilder().addAllMsgs(storageMsgs).build());
                triggerConversation(value);
                syncedMaxSeqs.put(conversationID, lastSeq);
            } else if (lastSeq != 0 && lastSeq > syncedMaxSeqs.get(conversationID)) {
                needSyncSeqMap.put(conversationID, new long[]{syncedMaxSeqs.get(conversationID) + 1, lastSeq});
            }
        }

        syncAndTriggerMsgs(needSyncSeqMap, DEFAULT_PULL_NUMS);
    }

    public void compareSeqsAndBatchSync(Map<String, Long> maxSeqToSync, int pullNums) {
        //int[] 为 int[2]
        var needSyncSeqMap = new HashMap<String, long[]>();
        if (reinstalled) {
            var notificationsSeqMap = new HashMap<String, Long>();
            var messagesSeqMap = new HashMap<String, Long>();
            for (Map.Entry<String, Long> entry : maxSeqToSync.entrySet()) {
                String conversationID = entry.getKey();
                long seq = entry.getValue();
                if (isNotification(conversationID)) {
                    notificationsSeqMap.put(conversationID, seq);
                } else {
                    messagesSeqMap.put(conversationID, seq);
                }
            }

            for (Map.Entry<String, Long> entry : notificationsSeqMap.entrySet()) {
                String conversationID = entry.getKey();
                long seq = entry.getValue();
                var err = ChatDbManager.getInstance().setNotificationSeq(conversationID, seq);
                if (err != null) {
                    LogcatHelper.logDInDebug(String.format("SetNotificationSeq err : %s, with conversationId: %s, and seq: %d", err, conversationID, seq));
                } else {
                    syncedMaxSeqs.put(conversationID, seq);
                }
            }

            for (Map.Entry<String, Long> entry : messagesSeqMap.entrySet()) {
                String conversationID = entry.getKey();
                long maxSeq = entry.getValue();
                if (syncedMaxSeqs.containsKey(conversationID)) {
                    long syncedMaxSeq = syncedMaxSeqs.get(conversationID);
                    if (maxSeq > syncedMaxSeq) {
                        needSyncSeqMap.put(conversationID, new long[]{syncedMaxSeq + 1, maxSeq});
                    }
                } else {
                    needSyncSeqMap.put(conversationID, new long[]{0, maxSeq});
                }
            }

            reinstalled = false;

        } else {
            for (Map.Entry<String, Long> entry : maxSeqToSync.entrySet()) {
                String conversationID = entry.getKey();
                long maxSeq = entry.getValue();
                if (syncedMaxSeqs.containsKey(conversationID)) {
                    long syncedMaxSeq = syncedMaxSeqs.get(conversationID);
                    if (maxSeq > syncedMaxSeq) {
                        needSyncSeqMap.put(conversationID, new long[]{syncedMaxSeq + 1, maxSeq});
                    }
                } else {
                    needSyncSeqMap.put(conversationID, new long[]{0, maxSeq});
                }
            }
        }
        syncAndTriggerMsgs(needSyncSeqMap, pullNums);
    }


    private boolean isNotification(String conversationID) {
        return conversationID.startsWith("n_");
    }

    private Exception syncAndTriggerMsgs(Map<String, long[]> seqMap, int syncMsgNum) {
        LogcatHelper.logDInDebug(String.format("websocket syncAndTriggerMsgs seqMap: %d, syncMsgNum: %d", seqMap.size(), syncMsgNum));
        if (seqMap != null && seqMap.size() > 0) {
            var tempSeqMap = new HashMap<String, long[]>(50);
            int msgNum = 0;
            for (var entry : seqMap.entrySet()) {
                var k = entry.getKey();
                var v = entry.getValue();
                long oneConversationSyncNum = v[1] - v[0] + 1;

                if ((oneConversationSyncNum / SPLIT_PULL_MSG_NUM) > 1 && isNotification(k)) {
                    Map<String, long[]> nSeqMap = new HashMap<>(1);
                    int count = (int) (oneConversationSyncNum / SPLIT_PULL_MSG_NUM);
                    long startSeq = v[0];
                    long end = 0;

                    for (int i = 0; i <= count; i++) {
                        if (i == count) {
                            nSeqMap.put(k, new long[]{startSeq, v[1]});
                        } else {
                            end = startSeq + SPLIT_PULL_MSG_NUM;
                            if (end > v[1]) {
                                end = v[1];
                                i = count; // Force exit the loop
                            }
                            nSeqMap.put(k, new long[]{startSeq, end});
                        }

                        ReturnWithErr<PullMessageBySeqsResp> returnWithErr = pullMsgBySeqRange(nSeqMap, syncMsgNum);
                        if (returnWithErr.getError() != null) {
                            return returnWithErr.getError();
                        }

                        var resp = returnWithErr.getPayload();
                        triggerConversation(resp.getMsgsMap());
                        triggerNotification(resp.getNotificationMsgsMap());

                        for (Map.Entry<String, long[]> nEntry : nSeqMap.entrySet()) {
                            syncedMaxSeqs.put(nEntry.getKey(), nEntry.getValue()[1]);
                        }
                        startSeq = end + 1;
                    }
                    continue;
                }

                tempSeqMap.put(k, v);
                if (oneConversationSyncNum > 0) {
                    msgNum += oneConversationSyncNum;
                }
                if (msgNum >= SPLIT_PULL_MSG_NUM) {
                    var returnWithErr = pullMsgBySeqRange(tempSeqMap, syncMsgNum);
                    if (returnWithErr.getError() != null) {
                        return returnWithErr.getError();
                    }
                    var resp = returnWithErr.getPayload();
                    triggerConversation(resp.getMsgsMap());
                    triggerNotification(resp.getNotificationMsgsMap());
                    for (var tempSeqEntry : tempSeqMap.entrySet()) {
                        var conversationID = tempSeqEntry.getKey();
                        syncedMaxSeqs.put(conversationID, tempSeqEntry.getValue()[1]);
                    }
                    tempSeqMap = new HashMap<String, long[]>(50);
                    msgNum = 0;
                }
            }

            var returnWithErr = pullMsgBySeqRange(tempSeqMap, syncMsgNum);
            if (returnWithErr.getError() != null) {
                return returnWithErr.getError();
            }
            var resp = returnWithErr.getPayload();
            triggerConversation(resp.getMsgsMap());
            triggerNotification(resp.getNotificationMsgsMap());
            for (var entry : seqMap.entrySet()) {
                var conversationID = entry.getKey();
                syncedMaxSeqs.put(conversationID, entry.getValue()[1]);
            }
        } else {
            LogcatHelper.logDInDebug(String.format("websocket noting conversation to sync: %d", syncMsgNum));
        }
        return null;
    }

    private ReturnWithErr<PullMessageBySeqsResp> pullMsgBySeqRange(Map<String, long[]> seqMap, int syncMsgNum) {
        var loginUserID = IMConfig.getInstance().userID;
        var reqBuilder = PullMessageBySeqsReq.newBuilder().setUserID(loginUserID);
        for (var entry : seqMap.entrySet()) {
            var conversationID = entry.getKey();
            var seqs = entry.getValue();
            var seqRange = SeqRange.newBuilder().setConversationID(conversationID).setBegin(seqs[0]).setEnd(seqs[1]).setNum(syncMsgNum).build();
            reqBuilder.addSeqRanges(seqRange);
        }

        ReturnWithSdkErr<PullMessageBySeqsResp> returnWithSdkErr = ConnectionManager.getInstance().getConnection()
            .sendReqWaitResp(ReqIdentifier.PULL_MSG_BY_SEQ_LIST, reqBuilder.build(), PullMessageBySeqsResp.parser());
        if (returnWithSdkErr.hasError()) {
            //error handling
            return new ReturnWithErr<>(returnWithSdkErr.getError());
        } else {
            var resp = returnWithSdkErr.getPayload();
            return new ReturnWithErr<PullMessageBySeqsResp>(resp, null);
        }
    }

    // triggers a conversation with a new message.
    private Exception triggerConversation(Map<String, PullMsgs> msgs) {
        if (msgs != null && msgs.size() >= 0) {
            var err = Trigger.triggerCmdNewMsgCome(new CmdNewMsgComeToConversation(msgs), OpenIMClient.getInstance().getConversationCh());
            return err;
        }
        return null;
    }

    private Exception triggerNotification(Map<String, PullMsgs> msgs) {
        if (msgs != null && msgs.size() >= 0) {
            var err = Trigger.triggerCmdNotification(new CmdNewMsgComeToConversation(msgs), OpenIMClient.getInstance().getConversationCh());
            return err;
        }
        return null;
    }
}
