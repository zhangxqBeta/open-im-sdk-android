package io.openim.android.sdk.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.openim.android.sdk.config.IMConfig;

@Database(entities = {LocalConversation.class, LocalSendingMessage.class}, version = 1, exportSchema = false)
public abstract class IMDatabase extends RoomDatabase {

    public abstract LocalConversationDao localConversationDao();

    public abstract LocalSendingMessageDao localSendingMessageDao();

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
        String dbPath = IMConfig.getInstance().dataDir + "/OpenIM_v3_" + uid + ".db";
        return dbPath;
    }
}
