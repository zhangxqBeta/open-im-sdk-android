package io.openim.android.sdk.interaction;

public class GeneralWsReq {

    public int reqIdentifier;
    public String token;
    public String sendID;
    public String operationID;
    public String msgIncr;
    public byte[] data;

    public GeneralWsReq(int reqIdentifier, String sendID, String operationID, String msgIncr, byte[] data) {
        this.reqIdentifier = reqIdentifier;
        this.sendID = sendID;
        this.operationID = operationID;
        this.msgIncr = msgIncr;
        this.data = data;
    }

    public int getReqIdentifier() {
        return reqIdentifier;
    }

    public void setReqIdentifier(int reqIdentifier) {
        this.reqIdentifier = reqIdentifier;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSendID() {
        return sendID;
    }

    public void setSendID(String sendID) {
        this.sendID = sendID;
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public String getMsgIncr() {
        return msgIncr;
    }

    public void setMsgIncr(String msgIncr) {
        this.msgIncr = msgIncr;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
