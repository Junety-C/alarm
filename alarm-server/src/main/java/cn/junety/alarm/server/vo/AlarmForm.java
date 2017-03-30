package cn.junety.alarm.server.vo;

import cn.junety.alarm.base.entity.Level;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by caijt on 2017/1/28.
 */
public class AlarmForm {
    private Integer code;
    private String content;
    private String routeKey;
    private String ip;
    private boolean isTest;
    private Level level;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRouteKey() {
        if (routeKey == null) {
            return "";
        }
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean validate() {
        if(code == null || StringUtils.isEmpty(content.trim()) || level == null) {
            return false;
        }
        return true;
    }
}
