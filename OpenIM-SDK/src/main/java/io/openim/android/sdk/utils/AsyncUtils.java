package io.openim.android.sdk.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncUtils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private static ExecutorService connectExecutor = Executors.newSingleThreadExecutor(new ImThreadFactory());


    public static void runOnUiThread(RunnableWrapper wrapper) {
        mHandler.post(wrapper);
    }

    public static void runOnConnectThread(Runnable runnable) {
        connectExecutor.submit(runnable);
    }


    private static class ImThreadFactory implements ThreadFactory {

        private static final AtomicInteger threadNumber = new AtomicInteger(1);


        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "construct_openim-" + threadNumber.getAndIncrement());
        }
    }
}
