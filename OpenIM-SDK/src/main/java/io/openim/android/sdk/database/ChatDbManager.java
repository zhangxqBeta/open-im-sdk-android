package io.openim.android.sdk.database;

import android.content.Context;
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
        var ss = imDatabase.localConversationDao().getAll();
        LogcatHelper.logDInDebug("openim size:" + ss.size());
    }

    public void checkSendingMessage() {
        //todo: impl
    }

}
