package io.openim.android.sdk.sdkdto;

import java.util.List;

public class GroupHasReadInfo {

    private List<String> hasReadUserIDList;
    private int hasReadCount;
    private int groupMemberCount;

    // Getters and Setters
    public List<String> getHasReadUserIDList() {
        return hasReadUserIDList;
    }

    public void setHasReadUserIDList(List<String> hasReadUserIDList) {
        this.hasReadUserIDList = hasReadUserIDList;
    }

    public int getHasReadCount() {
        return hasReadCount;
    }

    public void setHasReadCount(int hasReadCount) {
        this.hasReadCount = hasReadCount;
    }

    public int getGroupMemberCount() {
        return groupMemberCount;
    }

    public void setGroupMemberCount(int groupMemberCount) {
        this.groupMemberCount = groupMemberCount;
    }
}
