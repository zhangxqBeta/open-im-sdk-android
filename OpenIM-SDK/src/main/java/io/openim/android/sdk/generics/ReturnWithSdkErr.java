package io.openim.android.sdk.generics;

import io.openim.android.sdk.common.SdkException;

public class ReturnWithSdkErr<T> {

    private T payload;
    private SdkException error;

    public ReturnWithSdkErr(T payload, SdkException error) {
        this.payload = payload;
        this.error = error;
    }

    public ReturnWithSdkErr(T payload) {
        this.payload = payload;
    }

    public ReturnWithSdkErr(SdkException error) {
        this.error = error;
    }


    public T getPayload() {
        return payload;
    }

    public SdkException getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }
}
