package io.openim.android.sdk.connection;

public class ExponentialRetryStrategy {

    private int[] attempts;
    private int index;

    public ExponentialRetryStrategy() {
        attempts = new int[]{1, 2, 4, 8, 16, 32, 64};
        index = -1;
    }

    public int getSleepIntervalInMs() {
        index++;
        if (index >= attempts.length) {
            return attempts[attempts.length - 1] * 1000;
        }
        return attempts[index] * 1000;
    }

    public void reset() {
        index = -1;
    }

}
