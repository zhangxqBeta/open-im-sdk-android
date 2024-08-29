package io.openim.android.sdk.sdkdto;

import io.openim.android.sdk.protos.sdkws.ConversationUpdateTips;
import java.util.List;

public class ConversationUpdateTipsPojo {

    public String userID;
    public List<String> conversationIDList;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getConversationIDList() {
        return conversationIDList;
    }

    public void setConversationIDList(List<String> conversationIDList) {
        this.conversationIDList = conversationIDList;
    }

    public ConversationUpdateTips toProto() {
        return ConversationUpdateTips.newBuilder()
            .setUserID(userID)
            .addAllConversationIDList(conversationIDList)
            .build();
    }

}
