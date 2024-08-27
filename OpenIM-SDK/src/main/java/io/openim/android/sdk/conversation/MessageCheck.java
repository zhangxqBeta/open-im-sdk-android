package io.openim.android.sdk.conversation;

import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.connection.ConnectionManager;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.database.LocalErrChatLog;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.ConversationType;
import io.openim.android.sdk.enums.ReqIdentifier;
import io.openim.android.sdk.models.AdvancedMessage;
import io.openim.android.sdk.protos.sdkws.PullMessageBySeqsReq;
import io.openim.android.sdk.protos.sdkws.PullMessageBySeqsResp;
import io.openim.android.sdk.protos.sdkws.PullMsgs;
import io.openim.android.sdk.protos.sdkws.SeqRange;
import io.openim.android.sdk.utils.ConvertUtil;
import io.openim.android.sdk.utils.GeneralUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageCheck {

    public static List<ChatLog> faceURLAndNicknameHandle(List<ChatLog> self, List<ChatLog> others, String conversationID) {
        var lc = ChatDbManager.getInstance().getImDatabase().localConversationDao().getConversation(conversationID);
        if (lc == null) { //err
            self.addAll(others);
            return self;
        }

        switch (lc.conversationType) {
            case ConversationType.SINGLE_CHAT:
                singleHandle(self, others, lc);
                break;
            case ConversationType.GROUP_CHAT:
                groupHandle(self, others, lc);
                break;
        }
        self.addAll(others);
        return self;
    }

    //FaceURL and showName, we don't use it for now
    public static void singleHandle(List<ChatLog> self, List<ChatLog> others, LocalConversation lc) {

    }

    //FaceURL and showName, we don't use it for now
    public static void groupHandle(List<ChatLog> self, List<ChatLog> others, LocalConversation lc) {

    }

    public static void messageBlocksEndContinuityCheck(long minSeq, String conversationID, boolean notStartTime,
        boolean isReverse, int count, long startTime,
        List<ChatLog> list, AdvancedMessage messageListCallback) {
        if (minSeq != 0) {
            var seqList = new ArrayList<Long>();
            var seq = minSeq;
            long startSeq = seq - Constants.PULL_MSG_NUM_FOR_READ_DIFFUSION;
            if (startSeq <= 0) {
                startSeq = 1;
            }

            for (long i = startSeq; i < seq; i++) {
                seqList.add(i);
            }

            if (seqList.size() > 0) {
                pullMessageAndReGetHistoryMessages(conversationID, seqList, notStartTime, isReverse, count, startTime, list, messageListCallback);
            }

        } else {
            List<Long> seqList = Arrays.asList(0l, 0l);
            pullMessageAndReGetHistoryMessages(conversationID, seqList, notStartTime, isReverse, count, startTime, list, messageListCallback);
        }

    }

    /**
     * 检测消息块之间的连续性，如果不连续，则向前补齐,返回块之间是否连续，bool
     */
    public static boolean messageBlocksBetweenContinuityCheck(long lastMinSeq, long maxSeq, String conversationID, boolean notStartTime,
        boolean isReverse, int count, long startTime, List<ChatLog> list, AdvancedMessage messageListCallback) {
        if (lastMinSeq != 0) {
            if (maxSeq != 0) {
                if (maxSeq + 1 != lastMinSeq) {
                    long startSeq = (long) lastMinSeq - Constants.PULL_MSG_NUM_FOR_READ_DIFFUSION;
                    if (startSeq <= maxSeq) {
                        startSeq = (long) maxSeq + 1;
                    }
                    List<Long> successiveSeqList = new ArrayList<>();
                    for (long i = lastMinSeq - 1; i < startSeq; i++) {
                        successiveSeqList.add(i);
                    }
                    if (successiveSeqList.size() > 0) {
                        pullMessageAndReGetHistoryMessages(conversationID, successiveSeqList, notStartTime, isReverse, count, startTime, list,
                            messageListCallback);
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }

        } else {
            return true;
        }
        return false;
    }

    // 检测其内部连续性，如果不连续，则向前补齐,获取这一组消息的最大最小seq，以及需要补齐的seq列表长度
    public static ContinuityCheckResult messageBlocksInternalContinuityCheck(String conversationID, boolean notStartTime, boolean isReverse, int count,
        long startTime, List<ChatLog> list, AdvancedMessage messageListCallback) {
        var maxMinSeqList = getMaxAndMinHaveSeqList(list);
        var haveSeqList = maxMinSeqList.seqList;
        int lostSeqListLength = 0;
        if (maxMinSeqList.max != 0 && maxMinSeqList.min != 0) {

            var successiveSeqList = new ArrayList<Long>();
            for (long i = maxMinSeqList.min; i <= maxMinSeqList.max; i++) {
                successiveSeqList.add(i);
            }

            var lostSeqList = GeneralUtil.differenceSubset(successiveSeqList, haveSeqList);
            lostSeqListLength = lostSeqList.size();
            if (lostSeqListLength > 0) {
                List<Long> pullSeqList;
                if (lostSeqListLength <= Constants.PULL_MSG_NUM_FOR_READ_DIFFUSION) {
                    pullSeqList = lostSeqList;
                } else {
                    pullSeqList = lostSeqList.subList(lostSeqListLength - Constants.PULL_MSG_NUM_FOR_READ_DIFFUSION, lostSeqListLength);
                }

                pullMessageAndReGetHistoryMessages(conversationID, pullSeqList, notStartTime, isReverse, count, startTime, list, messageListCallback);
            }
        }
        return new ContinuityCheckResult(maxMinSeqList.max, maxMinSeqList.min, lostSeqListLength);
    }

    /**
     * 1、保证单次拉取消息量低于sdk单次从服务器拉取量 2、块中连续性检测 3、块之间连续性检测
     */

    public static void pullMessageAndReGetHistoryMessages(String conversationID, List<Long> seqList, boolean notStartTime, boolean isReverse, int count,
        long startTime, List<ChatLog> list, AdvancedMessage messageListCallback) {
        var localChatLogDao = ChatDbManager.getInstance().getImDatabase().chatLogDao();
        var existedSeqList = localChatLogDao.getAlreadyExistSeqList(conversationID, seqList);
        if (existedSeqList == null) {
            return;
        }

        if (existedSeqList.size() == seqList.size()) {
            return;
        }

        var newSeqList = GeneralUtil.differenceSubset(seqList, existedSeqList);
        if (newSeqList.size() == 0) {
            return;
        }
        var seqRange = SeqRange.newBuilder()
            .setConversationID(conversationID)
            .setBegin(newSeqList.get(0))
            .setEnd(newSeqList.get(newSeqList.size()) - 1)
            .setNum(newSeqList.size()).build();
        var pullMsgReq = PullMessageBySeqsReq.newBuilder().setUserID(IMConfig.getInstance().userID).addSeqRanges(seqRange).build();

        if (notStartTime && !ConnectionManager.getInstance().isConnected()) {
            return;
        }

        var returnWithSdkErr = ConnectionManager.getInstance().getConnection()
            .sendReqWaitResp(ReqIdentifier.PULL_MSG_BY_SEQ_LIST, pullMsgReq, PullMessageBySeqsResp.parser());

        if (returnWithSdkErr.hasError()) {
            list = errHandle(newSeqList, list, returnWithSdkErr.getError(), messageListCallback);
        } else {
            var pullMsgResp = returnWithSdkErr.getPayload();
            if (pullMsgResp.getMsgsMap() == null) {
                return;
            }

            if (pullMsgResp.getMsgsMap().containsKey(conversationID)) {
                var v = pullMsgResp.getMsgsMap().get(conversationID);
                pullMessageIntoTable(pullMsgResp.getMsgsMap(), conversationID);
                messageListCallback.setEnd(v.getIsEnd());

                if (notStartTime) {
                    list = localChatLogDao.getMessageListNoTime(conversationID, count, isReverse);
                } else {
                    list = localChatLogDao.getMessageList(conversationID, count, startTime, isReverse);
                }

            }
        }

    }

    public static void pullMessageIntoTable(Map<String, PullMsgs> pullMsgData, String inputConversationID) {
        var insertMsg = new HashMap<String, List<ChatLog>>(20);
        var updateMsg = new HashMap<String, List<ChatLog>>(30);
        var insertMessage = new ArrayList<ChatLog>();
        var updateMessage = new ArrayList<ChatLog>();
        var exceptionMsg = new ArrayList<LocalErrChatLog>();
        var selfInsertMessage = new ArrayList<ChatLog>();
        var ohtersInsertMessage = new ArrayList<ChatLog>();
        for (var entry : pullMsgData.entrySet()) {
            var conversationID = entry.getKey();
            var msgs = entry.getValue();
            for (var v : msgs.getMsgsList()) {
                var msg = ConvertUtil.convertToLocalChatLog(v);
                if (msg.status == Constants.MSG_STATUS_HAS_DELETED) {
                    insertMessage.add(msg);
                    continue;
                }
                msg.status = Constants.MSG_STATUS_SEND_SUCCESS;

                if (msg.clientMsgID == null || msg.clientMsgID.isEmpty()) {
                    exceptionMsg.add(ConvertUtil.convertToLocalErrChatLog(msg));
                    continue;
                }

                if (v.getSendID() == IMConfig.getInstance().userID) {
                    var m = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessage(conversationID, msg.clientMsgID);
                    if (m != null) {
                        if (m.seq == 0) {
                            updateMessage.add(msg);
                        } else {
                            exceptionMsg.add(ConvertUtil.convertToLocalErrChatLog(msg));
                        }
                    } else {
                        selfInsertMessage.add(msg);
                    }
                } else {
                    var oldMessage = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessage(conversationID, msg.clientMsgID);
                    if (oldMessage == null) {
                        ohtersInsertMessage.add(msg);
                    } else {
                        if (oldMessage.seq == 0) {
                            updateMessage.add(msg);
                        }
                    }
                }

                insertMessage.addAll(faceURLAndNicknameHandle(selfInsertMessage, ohtersInsertMessage, conversationID));
                insertMsg.put(conversationID, insertMessage);
                updateMsg.put(conversationID, updateMessage);
            }

            MessageController.batchUpdateMessageList(updateMsg);

            MessageController.batchInsertMessageList(insertMsg);
        }

    }

    //golang的实现，直接改传入的list指针
    public static List<ChatLog> errHandle(List<Long> seqList, List<ChatLog> list, Exception err,
        AdvancedMessage messageListCallback) {
        messageListCallback.setErrCode(100);
        messageListCallback.setErrMsg(err.getMessage());
        List<ChatLog> result = new ArrayList<>();
        var needPullMaxSeq = seqList.get(seqList.size() - 1);
        for (var chatLog : list) {
            if (chatLog.seq == 0 || chatLog.seq > needPullMaxSeq) {
                var temp = chatLog;
                result.add(temp);
            } else {
                if (chatLog.seq <= needPullMaxSeq) {
                    break;
                }
            }
        }

        //golang impl
        // *list = result

        return result;
    }

    public static MaxMinSeqList getMaxAndMinHaveSeqList(List<ChatLog> messages) {
        if (messages == null) {
            return new MaxMinSeqList();
        }

        List<Long> seqList = new ArrayList<>();
        long min = 0;
        long max = 0;

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).seq != 0) {
                seqList.add(messages.get(i).seq);
            }

            if (messages.get(i).seq != 0 && min == 0 && max == 0) {
                min = messages.get(i).seq;
                max = messages.get(i).seq;
            }

            if (messages.get(i).seq < min && messages.get(i).seq != 0) {
                min = messages.get(i).seq;
            }

            if (messages.get(i).seq > max) {
                max = messages.get(i).seq;
            }
        }

        return new MaxMinSeqList(max, min, seqList);
    }

    static class MaxMinSeqList {

        public long max;
        public long min;
        public List<Long> seqList;

        public MaxMinSeqList() {
        }

        public MaxMinSeqList(long max, long min, List<Long> seqList) {
            this.max = max;
            this.min = min;
            this.seqList = seqList;
        }
    }
}
