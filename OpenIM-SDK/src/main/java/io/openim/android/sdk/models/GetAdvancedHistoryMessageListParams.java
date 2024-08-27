package io.openim.android.sdk.models;

public class GetAdvancedHistoryMessageListParams {

    private long lastMinSeq;
    private String conversationID;
    private String startClientMsgID;
    private int count;

    public GetAdvancedHistoryMessageListParams(long lastMinSeq, String conversationID,
        int count) {
        this.lastMinSeq = lastMinSeq;
        this.conversationID = conversationID;

        this.count = count;
    }

    public long getLastMinSeq() {
        return lastMinSeq;
    }

    public void setLastMinSeq(long lastMinSeq) {
        this.lastMinSeq = lastMinSeq;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getStartClientMsgID() {
        return startClientMsgID;
    }

    public void setStartClientMsgID(String startClientMsgID) {
        this.startClientMsgID = startClientMsgID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
