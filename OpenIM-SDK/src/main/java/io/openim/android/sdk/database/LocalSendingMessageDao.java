package io.openim.android.sdk.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LocalSendingMessageDao {

    @Insert(onConflict = IGNORE)
    long insert(LocalSendingMessage localSendingMessageBean);

    @Delete
    void delete(LocalSendingMessage localSendingMessageBean);

    @Query("select * from local_sending_messages")
    List<LocalSendingMessage> getAll();
}
