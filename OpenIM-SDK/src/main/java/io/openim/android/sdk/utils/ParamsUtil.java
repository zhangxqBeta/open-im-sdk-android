package io.openim.android.sdk.utils;

import java.time.Clock;
import java.time.Instant;
import java.util.Random;

public class ParamsUtil {

    public static String buildOperationID() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String genMsgIncr(String userID) {
        return userID + "_" + buildOperationID();
    }

    public static String getMsgID(String sendID) {
        var t = String.valueOf(System.nanoTime());
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        return FileUtil.getMd5(t + sendID + rand.nextLong());
    }
}
