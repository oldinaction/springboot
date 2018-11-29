package cn.aezo.springboot.datasource.model;

/**
 * Created by smalle on 2017/9/12.
 */
public class GroupInfo {
    private Long groupId;

    private String groupName;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
