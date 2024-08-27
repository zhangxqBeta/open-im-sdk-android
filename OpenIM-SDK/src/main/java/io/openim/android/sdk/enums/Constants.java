package io.openim.android.sdk.enums;

public class Constants {

    public static final int ADD_CON_OR_UP_LAT_MSG = 2;
    public static final int UNREAD_COUNT_SET_ZERO = 3;
    public static final int INCR_UNREAD = 5;
    public static final int TOTAL_UNREAD_MESSAGE_CHANGED = 6;
    public static final int UPDATE_CON_FACE_URL_AND_NICKNAME = 7;
    public static final int UPDATE_LATEST_MESSAGE_CHANGE = 8;
    public static final int CON_CHANGE = 9;
    public static final int NEW_CON = 10;
    public static final int CON_CHANGE_DIRECT = 11;
    public static final int NEW_CON_DIRECT = 12;
    public static final int CONVERSATION_LATEST_MSG_HAS_READ = 13;
    public static final int UPDATE_MSG_FACE_URL_AND_NICKNAME = 14;
    public static final int SYNC_CONVERSATION = 15;
    public static final int SYNC_MESSAGE_LIST_REACTION_EXTENSIONS = 16;
    public static final int SYNC_MESSAGE_LIST_TYPE_KEY_INFO = 17;

    public static final int HAS_READ = 1;
    public static final int NOT_READ = 0;

    public static final int IS_FILTER = 1;
    public static final int NOT_FILTER = 0;

    public static final int MSG_SYNC_BEGIN = 1001;
    public static final int MSG_SYNC_END = 1003;
    public static final int MSG_SYNC_FAILED = 1004;

    public static final int RECEIVE_MESSAGE = 0;
    public static final int RECEIVE_NOT_NOTIFY_MESSAGE = 2;

    public static final String IS_HISTORY = "history";
    public static final String IS_PERSISTENT = "persistent";
    public static final String IS_SENDER_SYNC = "senderSync";
    public static final String IS_UNREAD_COUNT = "unreadCount";
    public static final String IS_CONVERSATION_UPDATE = "conversationUpdate";
    public static final String IS_NOT_PRIVATE = "notPrivate";
    public static final String IS_SENDER_CONVERSATION_UPDATE = "senderConversationUpdate";
    public static final String IS_OFFLINE_PUSH = "offlinePush";


    public static final int MSG_STATUS_SENDING = 1;
    public static final int MSG_STATUS_SEND_SUCCESS = 2;
    public static final int MSG_STATUS_SEND_FAILED = 3;
    public static final int MSG_STATUS_HAS_DELETED = 4;
    public static final int MSG_STATUS_FILTERED = 5;
    //MsgFrom
    public static final int USER_MSG_TYPE = 100;
    public static final int SYS_MSG_TYPE = 200;

    public static final int PULL_MSG_NUM_FOR_READ_DIFFUSION = 50;

    //connection status
    public static final int DEFAULT_NOT_CONNECT = 0;
    public static final int CLOSED = 2;
    public static final int CONNECTING = 3;
    public static final int CONNECTED = 4;

}
