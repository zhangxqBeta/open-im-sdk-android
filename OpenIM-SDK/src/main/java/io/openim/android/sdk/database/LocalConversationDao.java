package io.openim.android.sdk.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;

@Dao
public abstract class LocalConversationDao {

    @Insert(onConflict = IGNORE)
    public abstract long insert(LocalConversation conversationBean);

    @Insert(onConflict = IGNORE)
    public abstract void batchInsert(List<LocalConversation> localConversations);

    @Delete
    public abstract void delete(LocalConversation conversationBean);

    //return rows affected
    @Update
    public abstract int update(LocalConversation conversationBean);

    @Update
    public abstract int batchUpdateConversationList(List<LocalConversation> conversations);

    @Query("update local_conversations set unread_count=:unreadCount where conversation_id =:conversationID")
    public abstract void updateUnreadCount(String conversationID, int unreadCount);

    @Query("update local_conversations set has_read_seq=:hasReadSeq where conversation_id =:conversationID")
    public abstract void updateHasReadSeq(String conversationID, long hasReadSeq);

    @Query("update local_conversations set unread_count=:unreadCount, has_read_seq=:hasReadSeq where conversation_id =:conversationID")
    public abstract void updateUnreadCountAndHasReadSeq(String conversationID, int unreadCount, long hasReadSeq);

    @Query("update local_conversations set latest_msg_send_time=(:latestMsgSendTime), latest_msg=:latestMsg where conversation_id =:conversationID")
    public abstract void updateLatestMsgAndSendTime(String conversationID, long latestMsgSendTime, String latestMsg);

    //return rows affected
    @Query("update local_conversations set unread_count = unread_count+1 where conversation_id=:conversationID")
    public abstract int incrUnreadCount(String conversationID);

    @Query("update local_conversations set unread_count = unread_count-:unreadCount where conversation_id=:conversationID")
    public abstract int decrUnreadCount(String conversationID, long unreadCount);

    @Query("select unread_count from local_conversations where recv_msg_opt<:recvMsgOpt and latest_msg_send_time >:latestMsgSendTime")
    public abstract int[] getAllUnreadMsgCounts(int recvMsgOpt, long latestMsgSendTime);

    @Query("select * from local_conversations where conversation_id in (:conversationIDs)")
    public abstract List<LocalConversation> getMultipleConversationDB(String[] conversationIDs);

    @Query("select * from local_conversations")
    public abstract List<LocalConversation> getAllConversations();

    @Query("select * from local_conversations where conversation_id = :conversationID")
    public abstract LocalConversation getConversation(String conversationID);


    public Exception resetConversation(String conversationID) {
        try {
            var c = getConversation(conversationID);
            c.conversationID = conversationID;
            c.unreadCount = 0;
            c.latestMsg = "";
            c.latestMsgSendTime = 0;
            c.draftText = "";
            c.draftTextTime = 0;
            var affected = update(c);
            if (affected == 0) {
                return new Exception("ResetConversation failed-RowsAffected == 0");
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    @Transaction
    public void decrConversationUnreadCount(String conversationID, long count) {
        decrUnreadCount(conversationID, count);
        var conversation = getConversation(conversationID);
        if (conversation.unreadCount < 0) {
            updateUnreadCount(conversationID, 0);
        }
    }

    @Query("SELECT conversation_id FROM local_conversations;")
    public abstract List<String> getAllConversationIDList();

    @Query("SELECT * FROM local_conversations WHERE latest_msg_send_time > 0 ORDER BY CASE WHEN is_pinned = 1 THEN 0 ELSE 1 END, max(latest_msg_send_time, draft_text_time) DESC;")
    public abstract List<LocalConversation> getAllConversationListDB();

    @Query("select * from local_conversations where latest_msg_send_time = 0")
    public abstract List<LocalConversation> getHiddenConversationList();
}
