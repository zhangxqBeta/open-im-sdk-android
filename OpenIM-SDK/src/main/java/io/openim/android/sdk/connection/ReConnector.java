package io.openim.android.sdk.connection;

import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.utils.DelayUtils;

public class ReConnector {

    private final Connection connection;
    private int reConnectInterval = 1000;
    private final int what;

    public ReConnector(Connection connection) {
        this.connection = connection;
        this.what = DelayUtils.getWhat();
    }

    public void delayConnect() {
        LogcatHelper.logDInDebug(reConnectInterval + "毫秒之后开始重连");
//        DelayUtil.cancel(what);
//        DelayUtil.postDelay(connection::connect, reConnectInterval, what);
//        if (reConnectInterval < 30000) {
//            reConnectInterval += 1000;
//        }
    }

    public void cancelReConnect() {
        LogcatHelper.logDInDebug("cancelReConnect");
        reConnectInterval = 1000;
//        DelayUtil.cancel(what);
    }
}
