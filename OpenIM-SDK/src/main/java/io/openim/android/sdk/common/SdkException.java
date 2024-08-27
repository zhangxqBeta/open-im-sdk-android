package io.openim.android.sdk.common;

public class SdkException extends Exception {

    //golang impl: sdkerrs.UnknownCode
    public static final int sdkUnknownErrCode = 10005;
    //golang impl: sdkerrs.SdkInternalError
    public static final int sdInternalErrCode = 10006;

    public static final SdkException ErrUnreadCount = new SdkException(10303, "unread count has zero");
    public static final SdkException ErrArgs = new SdkException(10002, "ArgsError");

    public static final SdkException ErrMsgRepeated = new SdkException(10204, "only failed message can be repeatedly send");
    public static final SdkException ErrMsgContentTypeNotSupport = new SdkException(10205, "contentType not support currently");
    public static final SdkException ErrLoginRepeat = new SdkException(10102, "LoginRepeatError");
    public static final SdkException ErrLoginOut = new SdkException(10101, "LoginOutError");


    private int code;

    public static SdkException parseException(Exception e) {
        return new SdkException(0, e.getMessage());
    }

    // Constructor with just a message
//    public SdkException(String message) {
//        super(message);
//    }

    // Constructor with a message and an error code
    public SdkException(int code, String message) {
        super(message);
        this.code = code;
    }

    // Constructor with a message, error code, and a cause
    public SdkException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    // Getter for the error code
    public int getCode() {
        return code;
    }
}

