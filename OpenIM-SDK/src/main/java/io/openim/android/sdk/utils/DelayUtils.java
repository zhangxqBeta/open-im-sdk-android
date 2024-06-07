package io.openim.android.sdk.utils;

public class DelayUtils {

    private static int generatedWhat;

    public static synchronized int getWhat() {
        return generatedWhat++;
    }
}
