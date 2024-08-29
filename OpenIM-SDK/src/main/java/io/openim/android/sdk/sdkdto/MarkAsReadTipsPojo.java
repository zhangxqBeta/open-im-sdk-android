package io.openim.android.sdk.sdkdto;

import io.openim.android.sdk.protos.sdkws.MarkAsReadTips;
import java.util.List;

//pojo for proto MarkAsReadTips.class
public class MarkAsReadTipsPojo {

    public String markAsReadUserID;
    public String conversationID;
    public List<Long> seqs;
    public long hasReadSeq;


    public String getMarkAsReadUserID() {
        return markAsReadUserID;
    }

    public void setMarkAsReadUserID(String markAsReadUserID) {
        this.markAsReadUserID = markAsReadUserID;
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

    public long getHasReadSeq() {
        return hasReadSeq;
    }

    public void setHasReadSeq(long hasReadSeq) {
        this.hasReadSeq = hasReadSeq;
    }

    public MarkAsReadTips toProto() {
        return MarkAsReadTips.newBuilder()
            .setMarkAsReadUserID(markAsReadUserID)
            .setConversationID(conversationID)
            .setHasReadSeq(hasReadSeq)
            .addAllSeqs(seqs)
            .build();
    }
}
