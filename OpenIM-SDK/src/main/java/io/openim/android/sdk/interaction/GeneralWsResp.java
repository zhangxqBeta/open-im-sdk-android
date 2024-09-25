package io.openim.android.sdk.interaction;

import java.util.Arrays;

public class GeneralWsResp {

    public int reqIdentifier;
    public int errCode;
    public String errMsg;
    public String msgInc;
    public String operationID;
    public byte[] data;


    public GeneralWsResp() {
    }

    public int getReqIdentifier() {
        return reqIdentifier;
    }

    public void setReqIdentifier(int reqIdentifier) {
        this.reqIdentifier = reqIdentifier;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getMsgInc() {
        return msgInc;
    }

    public void setMsgInc(String msgInc) {
        this.msgInc = msgInc;
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GeneralWsResp{" +
            "reqIdentifier=" + reqIdentifier +
            ", errCode=" + errCode +
            ", errMsg='" + errMsg + '\'' +
            ", msgInc='" + msgInc + '\'' +
            ", operationID='" + operationID + '\'' +
            ", data=" + Arrays.toString(data) +
            '}';
    }
}
