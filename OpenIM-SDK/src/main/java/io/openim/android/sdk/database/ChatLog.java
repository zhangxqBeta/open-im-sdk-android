package io.openim.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import java.io.Serializable;

//java实现和golang不同，合同了同一个表，增加了conversationID field
//该表等效于chat_logs_si_xxxxx
@Entity(tableName = "chat_logs", primaryKeys = {"conversation_id", "client_msg_id"})
public class ChatLog implements Serializable {

    @NonNull
    @ColumnInfo(name = "conversation_id", index = true)
    public String conversationID;

    @NonNull
    @ColumnInfo(name = "client_msg_id", index = true)
    public String clientMsgID;

    @ColumnInfo(name = "server_msg_id")
    public String serverMsgID;

    @ColumnInfo(name = "send_id")
    public String sendID;

    @ColumnInfo(name = "recv_id", index = true)
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

    @ColumnInfo(name = "content_type", index = true)
    public int contentType;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "is_read")
    public boolean isRead;

    @ColumnInfo(name = "status")
    public int status;

    @ColumnInfo(name = "seq", index = true, defaultValue = "0")
    public long seq;

    @ColumnInfo(name = "send_time", index = true)
    public long sendTime;

    @ColumnInfo(name = "create_time")
    public long createTime;

    @ColumnInfo(name = "attached_info")
    public String attachedInfo;

    @ColumnInfo(name = "ex")
    public String ex;

    @ColumnInfo(name = "local_ex")
    public String localEx;

    @ColumnInfo(name = "is_react")
    public boolean isReact;

    @ColumnInfo(name = "is_external_extensions")
    public boolean isExternalExtensions;

    @ColumnInfo(name = "msg_first_modify_time")
    public long msgFirstModifyTime;

}