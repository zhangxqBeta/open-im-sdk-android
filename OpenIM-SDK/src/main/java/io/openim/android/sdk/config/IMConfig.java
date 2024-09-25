package io.openim.android.sdk.config;

import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.utils.ParamsUtil;

public class IMConfig {

    public boolean useNativeImpl;
    public String apiAddr;
    public String wsAddr;
    public String dataDir;

    public String userID;

    public String token;

    //android
    public int platformID = 2;

    public boolean isExternalExtensions = false;

    public static IMConfig getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static IMConfig instance = new IMConfig();
    }

    public String buildWsUrl() {
        String url = String.format("%s?sendID=%s&token=%s&platformID=%d&operationID=%s&isBackground=%b&compression=gzip&useJson=true", wsAddr, userID, token, 2,
            ParamsUtil.buildOperationID(), false);
        LogcatHelper.logDInDebug("websocket buildWsUrl url:" + url);
        return url;
    }

}
