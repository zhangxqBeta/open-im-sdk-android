package io.openim.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "local_notification_seqs")
public class NotificationSeqs implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "conversation_id")
    public String conversationID;

    @ColumnInfo(name = "seq")
    public Long seq;

    public NotificationSeqs(@NonNull String conversationID, Long seq) {
        this.conversationID = conversationID;
        this.seq = seq;
    }
}
