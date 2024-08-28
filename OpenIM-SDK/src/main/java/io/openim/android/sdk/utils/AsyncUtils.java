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
    
    private static ExecutorService listenerExecutor = Executors.newFixedThreadPool(10);

    private static ExecutorService httpAPIExecutor = Executors.newFixedThreadPool(5);


    public static void runOnUiThread(RunnableWrapper wrapper) {
        mHandler.post(wrapper);
    }

    public static void runOnConnectThread(Runnable runnable) {
        connectExecutor.submit(runnable);
    }

    public static void runOnListenerThread(Runnable runnable) {
        listenerExecutor.submit(runnable);
    }

    public static void runOnHttpAPIThread(Runnable runnable) {
        httpAPIExecutor.submit(runnable);
    }


    private static class ImThreadFactory implements ThreadFactory {

        private static final AtomicInteger threadNumber = new AtomicInteger(10);


        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "construct_openim-" + threadNumber.getAndIncrement());
        }
    }
}
