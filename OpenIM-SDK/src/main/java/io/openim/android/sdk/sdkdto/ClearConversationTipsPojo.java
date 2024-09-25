package io.openim.android.sdk.sdkdto;

import io.openim.android.sdk.protos.sdkws.ClearConversationTips;
import java.util.List;

public class ClearConversationTipsPojo {

    public String userID;
    public List<String> conversationIDs;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getConversationIDs() {
        return conversationIDs;
    }

    public List<String> getConversationIDsList() {
        return conversationIDs;
    }

    public void setConversationIDs(List<String> conversationIDs) {
        this.conversationIDs = conversationIDs;
    }

    public ClearConversationTips toProto() {
        return ClearConversationTips.newBuilder()
            .setUserID(userID)
            .addAllConversationIDs(conversationIDs)
            .build();
    }
}
