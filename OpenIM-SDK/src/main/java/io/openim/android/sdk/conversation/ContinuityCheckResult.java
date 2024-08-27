package io.openim.android.sdk.conversation;

public class ContinuityCheckResult {

    private long max;
    private long min;
    private int length;

    public ContinuityCheckResult(long max, long min, int length) {
        this.max = max;
        this.min = min;
        this.length = length;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
