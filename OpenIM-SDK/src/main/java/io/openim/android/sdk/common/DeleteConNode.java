package io.openim.android.sdk.common;

public class DeleteConNode {

    public String sourceID;
    public String conversationID;
    public int sessionType;

    public DeleteConNode(String sourceID, String conversationID, int sessionType) {
        this.sourceID = sourceID;
        this.conversationID = conversationID;
        this.sessionType = sessionType;
    }
}
