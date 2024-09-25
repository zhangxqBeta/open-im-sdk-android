package io.openim.android.sdk.conversation;

import java.util.concurrent.ConcurrentHashMap;

public class MaxSeqRecorder {

    private ConcurrentHashMap<String, Long> seqs = new ConcurrentHashMap<>();

    public long get(String conversationID) {
        return seqs.getOrDefault(conversationID, 0L);
    }

    public void set(String conversationID, long seq) {
        seqs.put(conversationID, seq);
    }

    public void incr(String conversationID, long num) {
        seqs.compute(conversationID, (key, val) -> (val == null ? num : val + num));
    }

    public boolean isNewMsg(String conversationID, long seq) {
        Long currentSeq = seqs.get(conversationID);
        return currentSeq == null || seq > currentSeq;
    }
}
