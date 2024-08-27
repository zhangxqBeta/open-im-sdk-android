package io.openim.android.sdk.conversation;

import io.openim.android.sdk.models.Message;
import java.util.Objects;

public class OnlineMsgKey {

    private String clientMsgID;
    private String serverMsgID;

    public OnlineMsgKey() {
    }

    public OnlineMsgKey(String clientMsgID, String serverMsgID) {
        this.clientMsgID = clientMsgID;
        this.serverMsgID = serverMsgID;
    }

    public String getClientMsgID() {
        return clientMsgID;
    }

    public void setClientMsgID(String clientMsgID) {
        this.clientMsgID = clientMsgID;
    }

    public String getServerMsgID() {
        return serverMsgID;
    }

    public void setServerMsgID(String serverMsgID) {
        this.serverMsgID = serverMsgID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(clientMsgID + serverMsgID, message.getClientMsgID() + message.getServerMsgID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientMsgID + serverMsgID);
    }
}
