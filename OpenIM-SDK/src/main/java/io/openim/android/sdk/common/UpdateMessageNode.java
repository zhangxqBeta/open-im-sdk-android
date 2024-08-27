package io.openim.android.sdk.common;

public class UpdateMessageNode {

    public int action;
    public Object args;

    public UpdateMessageNode(int action, Object args) {
        this.action = action;
        this.args = args;
    }
}
