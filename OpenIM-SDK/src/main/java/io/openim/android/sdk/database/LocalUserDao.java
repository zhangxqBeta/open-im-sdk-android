package io.openim.android.sdk.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LocalUserDao {

    //row id
    @Insert(onConflict = IGNORE)
    long insert(LocalUser localUser);

    @Query("select * from local_users where user_id = :userID")
    LocalUser getLoginUser(String userID);

    @Delete
    void delete(LocalUser localUser);

    @Update
    int update(LocalUser localUser);
}
