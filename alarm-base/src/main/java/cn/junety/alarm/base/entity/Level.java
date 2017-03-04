package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public enum Level {
    DEBUG("debug"), INFO("info"), ERROR("error");
    private String level;
    Level(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
