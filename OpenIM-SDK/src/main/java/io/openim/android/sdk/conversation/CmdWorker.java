package io.openim.android.sdk.conversation;

import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.internal.log.LogcatHelper;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

//所有的golang channel 复用同一个java Worker
public class CmdWorker extends Thread {

    private final BlockingQueue<Cmd2Value> cmdQueue;
    private volatile boolean running;


    public static CmdWorker getInstance() {
        return CmdWorker.SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static CmdWorker instance = new CmdWorker();
    }

    public CmdWorker() {
        this.cmdQueue = new LinkedBlockingQueue<>();
        this.running = true;
        this.start();
    }


    public static void sendCmd(BlockingQueue<Cmd2Value> ch, Cmd2Value value, long timeoutMs) throws InterruptedException {
        LogcatHelper.logDInDebug(String.format("websocket sendCmd value: %s", value));
        if (!ch.offer(value, timeoutMs, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("send cmd timeout");
        }
    }
}
