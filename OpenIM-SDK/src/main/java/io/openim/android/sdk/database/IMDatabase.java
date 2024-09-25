package io.openim.android.sdk.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.openim.android.sdk.config.IMConfig;

@Database(entities = {LocalConversation.class, LocalSendingMessage.class, NotificationSeqs.class, ChatLog.class, LocalChatLog.class,
    LocalErrChatLog.class, LocalUser.class}, version = 1, exportSchema = false)
public abstract class IMDatabase extends RoomDatabase {

    public abstract LocalConversationDao localConversationDao();

    public abstract LocalSendingMessageDao localSendingMessageDao();

    public abstract NotificationSeqsDao notificationSeqsDao();

    public abstract ChatLogDao chatLogDao();

    public abstract LocalChatLogDao localChatLogDao();

    public abstract LocalErrChatLogDao localErrChatLogDao();

    public abstract LocalUserDao localUserDao();

    private static volatile IMDatabase instance;

    public static IMDatabase getInstance(Context context, String uid) {
        if (instance == null) {
            synchronized (IMDatabase.class) {
                if (instance == null) {

                    instance = Room.databaseBuilder(context, IMDatabase.class, buildDbPath(uid))
                        .allowMainThreadQueries()
                        .setJournalMode(JournalMode.TRUNCATE)
                        .build();
                }
            }
        }
        return instance;
    }

    private static String buildDbPath(String uid) {
        String dbPath = IMConfig.getInstance().dataDir + "/OpenIM_native_" + uid + ".db";
        return dbPath;
    }
}
