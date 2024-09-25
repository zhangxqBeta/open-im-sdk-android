package io.openim.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "local_conversations")
public class LocalConversation implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "conversation_id")
    public String conversationID;

    @ColumnInfo(name = "conversation_type")
    public int conversationType;

    @ColumnInfo(name = "user_id")
    public String userID;

    @ColumnInfo(name = "group_id")
    public String groupID;

    @ColumnInfo(name = "show_name")
    public String showName;

    @ColumnInfo(name = "face_url")
    public String faceURL;

    @ColumnInfo(name = "recv_msg_opt")
    public int recvMsgOpt;

    @ColumnInfo(name = "unread_count")
    public int unreadCount;

    @ColumnInfo(name = "group_at_type")
    public int groupAtType;

    @ColumnInfo(name = "latest_msg")
    public String latestMsg;

    @ColumnInfo(name = "latest_msg_send_time", index = true)
    public long latestMsgSendTime;

    @ColumnInfo(name = "draft_text")
    public String draftText;

    @ColumnInfo(name = "draft_text_time")
    public int draftTextTime;

    @ColumnInfo(name = "is_pinned")
    public boolean isPinned;

    @ColumnInfo(name = "is_private_chat")
    public boolean isPrivateChat;

    @ColumnInfo(name = "burn_duration", defaultValue = "30")
    public int burnDuration;

    @ColumnInfo(name = "is_not_in_group")
    public boolean isNotInGroup;

    @ColumnInfo(name = "update_unread_count_time")
    public long updateUnreadCountTime;

    @ColumnInfo(name = "attached_info")
    public String attachedInfo;

    @ColumnInfo(name = "ex")
    public String ex;

    @ColumnInfo(name = "max_seq")
    public long maxSeq;

    @ColumnInfo(name = "min_seq")
    public long minSeq;

    @ColumnInfo(name = "has_read_seq")
    public long hasReadSeq;

    @ColumnInfo(name = "msg_destruct_time", defaultValue = "604800")
    public long msgDestructTime;

    @ColumnInfo(name = "is_msg_destruct")
    public boolean isMsgDestruct;

    @NonNull
    public String getConversationID() {
        return conversationID;
    }
}
