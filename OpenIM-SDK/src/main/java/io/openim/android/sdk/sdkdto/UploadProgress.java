package io.openim.android.sdk.sdkdto;

public class UploadProgress {

    private long total;
    private long save;
    private long current;
    private String uploadID;

    // Getters and Setters
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSave() {
        return save;
    }

    public void setSave(long save) {
        this.save = save;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public String getUploadID() {
        return uploadID;
    }

    public void setUploadID(String uploadID) {
        this.uploadID = uploadID;
    }
}
