package io.openim.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "local_users")
public class LocalUser {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    public String userID;

    @ColumnInfo(name = "name")
    public String nickname;

    @ColumnInfo(name = "face_url")
    public String faceURL;

    @ColumnInfo(name = "create_time")
    public long createTime;

    @ColumnInfo(name = "app_manger_level")
    public int appMangerLevel;

    @ColumnInfo(name = "ex")
    public String ex;

    @ColumnInfo(name = "attached_info")
    public String attachedInfo;

    @ColumnInfo(name = "global_recv_msg_opt")
    public int globalRecvMsgOpt;

}
