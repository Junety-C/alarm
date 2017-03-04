package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public class Module {
    private int id;
    private int projectId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                '}';
    }
}
