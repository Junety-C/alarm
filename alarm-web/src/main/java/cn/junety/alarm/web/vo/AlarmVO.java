package cn.junety.alarm.web.vo;

import cn.junety.alarm.base.entity.Alarm;
import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.base.entity.Project;
import com.alibaba.fastjson.JSON;


/**
 * Created by caijt on 2017/3/26.
 */
public class AlarmVO {
    private Alarm alarm;
    private Project project;
    private Module module;
    private Group group;
    private String config;

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getConfig() {
        AlarmConfig alarmConfig = JSON.parseObject(alarm.getConfig(), AlarmConfig.class);
        config = "";
        if(alarmConfig.isMail()) config += "邮件,";
        if(alarmConfig.isWechat()) config += "微信,";
        if(alarmConfig.isSms()) config += "短信,";
        if(alarmConfig.isQq()) config += "QQ,";

        if(config.length() > 0) {
            config = config.substring(0, config.length()-1);
        }
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
