package io.openim.android.sdk.config;

import io.openim.android.sdk.internal.log.LogcatHelper;

public class IMConfig {

    public boolean useNativeImpl;
    public String apiAddr;
    public String wsAddr;
    public String dataDir;

    public String userID;

    public String token;

    public static IMConfig getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static IMConfig instance = new IMConfig();
    }

    public String buildWsUrl() {
        String url = String.format("%s?sendID=%s&token=%s&platformID=%d&operationID=%s&isBackground=%b&compression=gzip", wsAddr, userID, token, 2,
            "1717662622665418336", false);
        LogcatHelper.logDInDebug("buildWsUrl url" + url);
        return url;
    }

}
