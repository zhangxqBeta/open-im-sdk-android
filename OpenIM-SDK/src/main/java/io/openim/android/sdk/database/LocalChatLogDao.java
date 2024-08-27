package io.openim.android.sdk.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.enums.ConversationType;
import java.util.List;

@Dao
public abstract class LocalChatLogDao {

    //return rowId, long value
    @Insert(onConflict = IGNORE)
    public abstract long insert(ChatLog localChatLog);

    //return an array or a collection of long values instead
    @Insert(onConflict = IGNORE)
    public abstract List<Long> bulkInsert(List<ChatLog> localChatLogs);

    public int updateMessageStatusBySourceID(String sourceID, int status, int sessionType) {
        if (sourceID.equals(IMConfig.getInstance().userID) && sessionType == ConversationType.SINGLE_CHAT) {
            return updateMessageStatusBySourceIDForSingleChatTypeAndLoginUser(sourceID, status, sessionType);
        } else {
            return updateMessageStatusBySourceIDForNonSingleChatTypeOrLoginUser(sourceID, status, sessionType);
        }
    }

    @Query("update local_chat_logs set status=:status where (send_id=:sourceID or recv_id=:sourceID) and session_type=:sessionType")
    public abstract int updateMessageStatusBySourceIDForNonSingleChatTypeOrLoginUser(String sourceID, int status, int sessionType);


    @Query("update local_chat_logs set status=:status where send_id=:sourceID and recv_id=:sourceID and session_type=:sessionType")
    public abstract int updateMessageStatusBySourceIDForSingleChatTypeAndLoginUser(String sourceID, int status, int sessionType);


}
