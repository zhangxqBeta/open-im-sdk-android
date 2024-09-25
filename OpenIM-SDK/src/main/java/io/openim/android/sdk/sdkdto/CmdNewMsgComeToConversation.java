package io.openim.android.sdk.sdkdto;

import io.openim.android.sdk.protos.sdkws.PullMsgs;
import java.util.Map;

public class CmdNewMsgComeToConversation {

    public Map<String, PullMsgs> msgs;
    public int syncFlag;

    public CmdNewMsgComeToConversation(Map<String, PullMsgs> msgs) {
        this.msgs = msgs;
    }

    public CmdNewMsgComeToConversation(int syncFlag) {
        this.syncFlag = syncFlag;
    }

    @Override
    public String toString() {
        return "CmdNewMsgComeToConversation{" +
            "msgs=" + msgs +
            ", syncFlag=" + syncFlag +
            '}';
    }
}
