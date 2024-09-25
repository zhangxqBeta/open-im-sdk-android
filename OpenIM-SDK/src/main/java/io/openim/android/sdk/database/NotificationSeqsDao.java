package io.openim.android.sdk.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NotificationSeqsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotificationSeqs notificationSeqs);

    @Delete
    void delete(NotificationSeqs notificationSeqs);

    @Query("select * from local_notification_seqs;")
    List<NotificationSeqs> getNotificationAllSeqs();


}
