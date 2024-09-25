package io.openim.android.sdk.http;

public class ServerApiRouter {

    public static final String RouterMsg = "/msg";

    public static final String ConversationGroup = "/conversation";
    public static final String GetConversationsRouter = ConversationGroup + "/get_conversations";
    public static final String GetConversationsHasReadAndMaxSeqRouter = RouterMsg + "/get_conversations_has_read_and_max_seq";

    public static final String SetConversationHasReadSeq = RouterMsg + "/set_conversation_has_read_seq";

    public static final String GetUsersInfoRouter = "/user/get_users_info";

    public static final String GetAllConversationsRouter = ConversationGroup + "/get_all_conversations";
    public static final String MarkConversationAsRead = RouterMsg + "/mark_conversation_as_read";

    public static final String ClearConversationMsgRouter = RouterMsg + "/clear_conversation_msg"; // Clear the message of the specified conversation


}
