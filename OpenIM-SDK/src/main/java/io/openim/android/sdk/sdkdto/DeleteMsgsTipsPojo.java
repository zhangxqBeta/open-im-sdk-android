package io.openim.android.sdk.sdkdto;

import io.openim.android.sdk.protos.sdkws.DeleteMsgsTips;
import java.util.List;

public class DeleteMsgsTipsPojo {

    public String userID;
    public String conversationID;
    public List<Long> seqs;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public List<Long> getSeqs() {
        return seqs;
    }

    public List<Long> getSeqsList() {
        return getSeqs();
    }

    public void setSeqs(List<Long> seqs) {
        this.seqs = seqs;
    }

    public DeleteMsgsTips toProto() {
        return DeleteMsgsTips.newBuilder()
            .setUserID(userID)
            .setConversationID(conversationID)
            .addAllSeqs(seqs)
            .build();
    }

}
