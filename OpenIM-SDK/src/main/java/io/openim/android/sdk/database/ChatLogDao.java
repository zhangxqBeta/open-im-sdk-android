package io.openim.android.sdk.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public abstract class ChatLogDao {

    //return rowId, long value
    @Insert(onConflict = IGNORE)
    public abstract long insert(ChatLog localChatLog);

    //return an array or a collection of long values instead
    @Insert(onConflict = IGNORE)
    public abstract List<Long> bulkInsert(List<ChatLog> localChatLogs);

    @Delete
    public abstract void delete(ChatLog localChatLog);

    @Update
    public abstract int update(ChatLog localChatLog);

    @Query("select * from chat_logs where conversation_id=:conversationID and seq IN (:seqs) order by send_time DESC")
    public abstract List<ChatLog> getMessagesBySeqs(String conversationID, List<Long> seqs);

    //1: constant.HasRead
    @Query("update chat_logs set is_read=1 where conversation_id=:conversationID and  seq in (:seqs) and send_id!= :loginUserID")
    public abstract int markConversationMessageAsReadBySeqs(String conversationID, List<Long> seqs, String loginUserID);

    @Query("update chat_logs set status=:status, send_time=:sendTime, server_msg_id=:serverMsgID where conversation_id=:conversationID and client_msg_id=:clientMsgID and seq=0")
    public abstract void updateMessageTimeAndStatus(String conversationID, String clientMsgID, String serverMsgID, long sendTime, int status);

    @Query("select IFNULL(max(seq),0) from chat_logs where conversation_id = :conversationID")
    public abstract long getConversationNormalMsgSeq(String conversationID);

    @Query("select IFNULL(max(seq),0) from chat_logs where conversation_id = :conversationID and send_id!= (:loginUserID)")
    public abstract long getConversationPeerNormalMsgSeq(String conversationID, String loginUserID);

    //4:constant.MsgStatusHasDeleted
    @Query("update chat_logs set status = 4 where conversation_id=:conversationID")
    public abstract void markDeleteConversationAllMessages(String conversationID);

    @Query("delete from chat_logs where conversation_id=:conversationID")
    public abstract void deleteConversationAllMessages(String conversationID);

    @Query("select * from chat_logs where conversation_id=:conversationID and seq=:seq")
    public abstract ChatLog getMessageBySeq(String conversationID, long seq);

    @Query("select * from chat_logs where conversation_id=:conversationID and client_msg_id=:clientMsgID")
    public abstract ChatLog getMessage(String conversationID, String clientMsgID);

    @Query("delete from chat_logs where conversation_id=:conversationID and client_msg_id in (:msgIDs)")
    public abstract void deleteConversationMsgs(String conversationID, String[] msgIDs);


    /**
     * isReverse=true for golang impl
     */
    @Query("select * from chat_logs where conversation_id=:conversationID order by send_time ASC limit :count offset 0")
    abstract List<ChatLog> getMessageListNoTimeASC(String conversationID, int count);

    /**
     * isReverse=false for golang impl
     */
    @Query("select * from chat_logs where conversation_id=:conversationID order by send_time DESC limit :count offset 0")
    public abstract List<ChatLog> getMessageListNoTimeDESC(String conversationID, int count);


    @Query("select * from chat_logs where conversation_id=:conversationID and send_time order by send_time>(:startTime) DESC limit :count offset 0")
    abstract List<ChatLog> getMessageListASC(String conversationID, int count, long startTime);

    @Query("select * from chat_logs where conversation_id=:conversationID and send_time order by send_time<(:startTime) DESC limit :count offset 0")
    abstract List<ChatLog> getMessageListDESC(String conversationID, int count, long startTime);

    public List<ChatLog> getMessageList(String conversationID, int count, long startTime, boolean isReverse) {
        if (isReverse) {
            return getMessageListASC(conversationID, count, startTime);
        } else {
            return getMessageListDESC(conversationID, count, startTime);
        }
    }

    public List<ChatLog> getMessageListNoTime(String conversationID, int count, boolean isReverse) {
        if (isReverse) {
            return getMessageListNoTimeASC(conversationID, count);
        } else {
            return getMessageListNoTimeDESC(conversationID, count);
        }
    }

    @Query("select seq from chat_logs where conversation_id = (:conversationID) and  seq in (:lostSeqList)")
    public abstract List<Long> getAlreadyExistSeqList(String conversationID, List<Long> lostSeqList);

    // 0: constant.NotRead
    @Query("select * from chat_logs where conversation_id=:conversationID and send_id!=(:loginUserID) and is_read = 0")
    public abstract List<ChatLog> getUnreadMessage(String conversationID, String loginUserID);

    @Query("select * from chat_logs where conversation_id=:conversationID and client_msg_id in (:msgIDs) and send_id!=(:loginUserID)")
    public abstract List<ChatLog> getMessagesByIDs(String conversationID, List<String> msgIDs, String loginUserID);


}
