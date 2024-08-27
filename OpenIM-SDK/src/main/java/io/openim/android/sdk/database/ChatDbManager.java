package io.openim.android.sdk.database;

import android.content.Context;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.internal.log.LogcatHelper;

public class ChatDbManager {

    private IMDatabase imDatabase;

    public static ChatDbManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static ChatDbManager instance = new ChatDbManager();
    }

    public void init(Context context, String uid) {
        imDatabase = IMDatabase.getInstance(context, uid);
//        var ss = imDatabase.localConversationDao().getAllConversations();
//        LogcatHelper.logDInDebug("openim size:" + ss.size());
    }

    public IMDatabase getImDatabase() {
        return imDatabase;
    }


    //return error message
    public Exception setNotificationSeq(String conversationId, long seq) {
        imDatabase.notificationSeqsDao().insert(new NotificationSeqs(conversationId, seq));
        return null;
    }

    public int getTotalUnreadMsgCountDB() {
        int[] res = imDatabase.localConversationDao().getAllUnreadMsgCounts(Constants.RECEIVE_NOT_NOTIFY_MESSAGE, 0);
        int totalUnreadCount = 0;
        for (int val : res) {
            totalUnreadCount += val;
        }
        return totalUnreadCount;
    }

}
