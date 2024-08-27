package io.openim.android.sdk.common;

public class Cmd2Value {

    public String cmd;
    public Object value;

    public Cmd2Value(Object value) {
        this.value = value;
    }

    public Cmd2Value(String cmd, Object value) {
        this.cmd = cmd;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Cmd2Value{" +
            "cmd='" + cmd + '\'' +
            ", value=" + value +
            '}';
    }
}
