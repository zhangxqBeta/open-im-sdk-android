package io.openim.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "local_sending_messages", primaryKeys = {"conversation_id", "client_msg_id"})
public class LocalSendingMessage implements Serializable {

    @NonNull
    @ColumnInfo(name = "conversation_id")
    public String conversationID;

    @NonNull
    @ColumnInfo(name = "client_msg_id")
    public String clientMsgID;

    @ColumnInfo(name = "ex")
    public String ex;

    public LocalSendingMessage(@NonNull String conversationID, @NonNull String clientMsgID) {
        this.conversationID = conversationID;
        this.clientMsgID = clientMsgID;
    }
}
