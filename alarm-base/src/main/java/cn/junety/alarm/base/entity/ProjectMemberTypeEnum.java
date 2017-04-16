package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/4/16.
 */
public enum ProjectMemberTypeEnum {
    // 管理员
    ADMIN_MEMBER(0),
    // 普通用户
    NORMAL_MEMBER(1),;

    private Integer value;

    ProjectMemberTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
