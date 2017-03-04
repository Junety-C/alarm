package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public class GroupMember {
    private int groupId;
    private int receiverId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
