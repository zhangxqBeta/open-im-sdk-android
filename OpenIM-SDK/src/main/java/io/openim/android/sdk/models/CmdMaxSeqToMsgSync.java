package io.openim.android.sdk.models;

import java.util.Map;

public class CmdMaxSeqToMsgSync {

    public Map<String, Long> conversationMaxSeqOnSvr;

    public CmdMaxSeqToMsgSync() {
    }

    public CmdMaxSeqToMsgSync(Map<String, Long> conversationMaxSeqOnSvr) {
        this.conversationMaxSeqOnSvr = conversationMaxSeqOnSvr;
    }

    @Override
    public String toString() {
        return "CmdMaxSeqToMsgSync{" +
            "conversationMaxSeqOnSvr=" + conversationMaxSeqOnSvr +
            '}';
    }
}
