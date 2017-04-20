package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public class AlarmLog {
    private long id;
    private long reportId;
    private int code;
    private String alarmName;
    private String projectName;
    private String moduleName;
    private String groupName;
    private Level level;
    private String receivers;
    private String content;
    private String ip;
    private int status;
    private String deliveryStatus;
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", reportId=" + reportId +
                ", code=" + code +
                ", alarmName='" + alarmName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", ModuleName='" + moduleName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", level=" + level +
                ", receivers='" + receivers + '\'' +
                ", content='" + content + '\'' +
                ", ip='" + ip + '\'' +
                ", status=" + status +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
