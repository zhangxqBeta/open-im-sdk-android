package io.openim.android.sdk.common;

public class UpdateConNode {

    public String conID;
    public int action;
    public Object args;

    public UpdateConNode(String conID, int action, Object args) {
        this.conID = conID;
        this.action = action;
        this.args = args;
    }

    public UpdateConNode(int action) {
        this.action = action;
    }

    public UpdateConNode(String conID, int action) {
        this.conID = conID;
        this.action = action;
    }

    public UpdateConNode(int action, Object args) {
        this.action = action;
        this.args = args;
    }
}
