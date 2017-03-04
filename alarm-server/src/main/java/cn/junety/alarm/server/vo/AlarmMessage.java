package cn.junety.alarm.server.vo;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.base.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public class AlarmMessage {
    private int code;
    private String alarmName;
    private String routeKey;
    private String projectName;
    private String moduleName;
    private Level level;
    private List<Receiver> receivers;
    private String content;
    private String ip;
    private AlarmStatus status;
    private long createTime;
    private String config;
    private long reportId;
    private long logId;

    public AlarmMessage() {
        this.createTime = System.currentTimeMillis();
    }

    public AlarmMessage addAlarmForm(AlarmForm alarmForm) {
        this.level = alarmForm.getLevel();
        this.content = alarmForm.getContent();
        this.ip = alarmForm.getIp();
        return this;
    }

    public AlarmMessage addAlarm(Alarm alarm) {
        this.code = alarm.getCode();
        this.alarmName = alarm.getName();
        this.routeKey = alarm.getRouteKey();
        this.config = alarm.getConfig();
        return this;
    }

    public AlarmMessage addProject(Project project) {
        this.projectName = project.getName();
        return this;
    }

    public AlarmMessage addModule(Module module) {
        this.moduleName = module.getName();
        return this;
    }

    public AlarmMessage addReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
        return this;
    }

    public AlarmMessage addReportId(long reportId) {
        this.reportId = reportId;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Level getLevel() {
        return level;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public String getContent() {
        return content;
    }

    // substring content
    public String getContent(int length) {
        if(content.length() <= length) {
            return content;
        } else {
            return content.substring(0, length);
        }
    }

    public String getIp() {
        return ip;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getCreatetimeFormat() {
        return DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, new Date(createTime));
    }

    public String getConfig() {
        return config;
    }

    public long getReportId() {
        return reportId;
    }

    public AlarmMessage addLogId(long logId) {
        this.logId = logId;
        return this;
    }

    public long getLogId() {
        return logId;
    }

    public List<String> getWechatList() {
        List<String> wechatList = new ArrayList<>();
        for(Receiver receiver : receivers) {
            wechatList.add(receiver.getWechat());
        }
        return wechatList;
    }

    public List<String> getMailList() {
        List<String> mailList = new ArrayList<>();
        for(Receiver receiver : receivers) {
            mailList.add(receiver.getMail());
        }
        return mailList;
    }

    public List<String> getQQList() {
        List<String> qqList = new ArrayList<>();
        for(Receiver receiver : receivers) {
            qqList.add(receiver.getQq());
        }
        return qqList;
    }

    public List<String> getPhoneList() {
        List<String> phoneList = new ArrayList<>();
        for(Receiver receiver : receivers) {
            phoneList.add(receiver.getPhone());
        }
        return phoneList;
    }
}
