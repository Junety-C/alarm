package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public enum AlarmStatus {
    CREATE("创建"), SEND("发送"), INHIBITION("抑制"), TEST("测试");

    private String tag;

    AlarmStatus(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}