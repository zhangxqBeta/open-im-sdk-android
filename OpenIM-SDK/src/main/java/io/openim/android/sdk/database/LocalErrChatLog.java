package io.openim.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "local_err_chat_logs")
public class LocalErrChatLog {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "seq")
    public long seq;

    @ColumnInfo(name = "client_msg_id")
    public String clientMsgID;

    @ColumnInfo(name = "server_msg_id")
    public String serverMsgID;

    @ColumnInfo(name = "send_id")
    public String sendID;

    @ColumnInfo(name = "recv_id")
    public String recvID;

    @ColumnInfo(name = "sender_platform_id")
    public int senderPlatformID;

    @ColumnInfo(name = "sender_nick_name")
    public String senderNickname;

    @ColumnInfo(name = "sender_face_url")
    public String senderFaceURL;

    @ColumnInfo(name = "session_type")
    public int sessionType;

    @ColumnInfo(name = "msg_from")
    public int msgFrom;

    @ColumnInfo(name = "content_type")
    public int contentType;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "is_read")
    public boolean isRead;

    @ColumnInfo(name = "status")
    public int status;

    @ColumnInfo(name = "send_time")
    public long sendTime;

    @ColumnInfo(name = "create_time")
    public long createTime;

    @ColumnInfo(name = "attached_info")
    public String attachedInfo;

    @ColumnInfo(name = "ex")
    public String ex;
}
