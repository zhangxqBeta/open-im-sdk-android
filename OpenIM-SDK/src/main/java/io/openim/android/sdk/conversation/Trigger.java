package io.openim.android.sdk.conversation;

import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.enums.Cmd;
import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.models.CmdMaxSeqToMsgSync;
import io.openim.android.sdk.protos.sdkws.PushMessages;
import io.openim.android.sdk.sdkdto.CmdNewMsgComeToConversation;
import java.util.concurrent.BlockingQueue;

public class Trigger {

    private static final long sendCmdTimeoutMs = 100;

    public static Exception triggerCmdPushMsg(PushMessages msg, BlockingQueue<Cmd2Value> pushMsgAndMaxSeqCh) {
        var c2v = new Cmd2Value(Cmd.CMD_PUSH_MSG, msg);
        try {
            CmdWorker.getInstance().sendCmd(pushMsgAndMaxSeqCh, c2v, sendCmdTimeoutMs);
        } catch (InterruptedException e) {
            return e;
        }
        return null;
    }

    public static Exception triggerCmdMaxSeq(CmdMaxSeqToMsgSync seq, BlockingQueue<Cmd2Value> pushMsgAndMaxSeqCh) {
        var c2v = new Cmd2Value(Cmd.CMD_MAX_SEQ, seq);
        try {
            CmdWorker.getInstance().sendCmd(pushMsgAndMaxSeqCh, c2v, sendCmdTimeoutMs);
        } catch (InterruptedException e) {
            return e;
        }
        return null;
    }


    public static Exception triggerCmdLogOut(BlockingQueue<Cmd2Value> ch) {
        var c2v = new Cmd2Value(Cmd.CMD_LOG_OUT);
        try {
            CmdWorker.getInstance().sendCmd(ch, c2v, sendCmdTimeoutMs);
        } catch (InterruptedException e) {
            return e;
        }
        return null;
    }

    public static Exception triggerCmdNewMsgCome(CmdNewMsgComeToConversation msg, BlockingQueue<Cmd2Value> conversationCh) {
        var c2v = new Cmd2Value(Cmd.CMD_NEW_MSG_COME, msg);
        try {
            CmdWorker.getInstance().sendCmd(conversationCh, c2v, sendCmdTimeoutMs);
        } catch (InterruptedException e) {
            return e;
        }
        return null;
    }

    public static Exception triggerCmdNotification(CmdNewMsgComeToConversation msg, BlockingQueue<Cmd2Value> conversationCh) {
        var c2v = new Cmd2Value(Cmd.CMD_NOTIFICATION, msg);
        try {
            CmdWorker.getInstance().sendCmd(conversationCh, c2v, sendCmdTimeoutMs);
        } catch (InterruptedException e) {
            return e;
        }
        return null;
    }

    public static Exception triggerCmdUpdateConversation(UpdateConNode node, BlockingQueue<Cmd2Value> conversationCh) {
        var c2v = new Cmd2Value(Cmd.CMD_UPDATE_CONVERSATION, node);
        try {
            CmdWorker.getInstance().sendCmd(conversationCh, c2v, sendCmdTimeoutMs);
        } catch (InterruptedException e) {
            return e;
        }
        return null;
    }

}
