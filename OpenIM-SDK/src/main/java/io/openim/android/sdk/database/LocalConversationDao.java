package io.openim.android.sdk.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LocalConversationDao {

    @Insert(onConflict = IGNORE)
    long insert(LocalConversation conversationBean);

    @Delete
    void delete(LocalConversation conversationBean);

    @Query("select * from local_conversations")
    List<LocalConversation> getAll();
}
