package io.openim.android.sdk.generics;

public class ReturnWithErr<T> {

    private T payload;
    private Exception error;

    public ReturnWithErr(T payload, Exception error) {
        this.payload = payload;
        this.error = error;
    }

    public ReturnWithErr(T payload) {
        this.payload = payload;
    }

    public ReturnWithErr(Exception error) {
        this.error = error;
    }


    public T getPayload() {
        return payload;
    }

    public Exception getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }
}
