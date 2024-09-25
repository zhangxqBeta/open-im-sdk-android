package io.openim.android.sdk.conversation;

import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.protos.conversation.Conversation;

public class Convert {

    public static LocalConversation serverConversationToLocal(Conversation conversation) {
        var localConversation = new LocalConversation();
        localConversation.conversationID = conversation.getConversationID();
        localConversation.conversationType = conversation.getConversationType();
        localConversation.userID = conversation.getUserID();
        localConversation.groupID = conversation.getGroupID();
        localConversation.recvMsgOpt = conversation.getRecvMsgOpt();
        localConversation.groupAtType = conversation.getGroupAtType();
        localConversation.isPinned = conversation.getIsPinned();
        localConversation.burnDuration = conversation.getBurnDuration();
        localConversation.isPrivateChat = conversation.getIsPrivateChat();
        localConversation.attachedInfo = conversation.getAttachedInfo();
        localConversation.ex = conversation.getEx();
        localConversation.msgDestructTime = conversation.getMsgDestructTime();
        localConversation.isMsgDestruct = conversation.getIsMsgDestruct();
        return localConversation;
    }

}
