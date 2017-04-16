package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public class Project {
    private int id;
    private String name;
    private String creater;
    private Long createTime;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creater='" + creater + '\'' +
                ", createTime=" + createTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
