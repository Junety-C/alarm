package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/4/2.
 */
public enum  UserTypeEnum {
    //管理员
    ADMIN_USER(0),
    //普通用户
    REGULAR_USER(2),
    //不存在用户
    NO_USER(-1);

    private Integer value;

    UserTypeEnum(Integer value) {
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
