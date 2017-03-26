package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public class Alarm {
    private int id;
    private int code;
    private String name;
    private int projectId;
    private int moduleId;
    private int groupId;
    private String routeKey;
    private String config;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", projectId=" + projectId +
                ", moduleId=" + moduleId +
                ", groupId=" + groupId +
                ", routeKey='" + routeKey + '\'' +
                ", config='" + config + '\'' +
                '}';
    }
}
