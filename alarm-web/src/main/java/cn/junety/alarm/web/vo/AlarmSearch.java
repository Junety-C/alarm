package cn.junety.alarm.web.vo;

import cn.junety.alarm.base.entity.User;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/26.
 */
public class AlarmSearch {
    private Page page;
    private Integer userId;
    private Integer code;
    private String alarmName;
    private String projectName;
    private String groupName;

    public AlarmSearch(HttpServletRequest request, User user) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);
        this.userId = user.getId();

        if(!Strings.isNullOrEmpty(request.getParameter("code"))) {
            this.code = NumberUtils.toInt(request.getParameter("code"), 0);
        } else if(!Strings.isNullOrEmpty(request.getParameter("alarm_name"))) {
            this.alarmName = request.getParameter("alarm_name");
        } else if(!Strings.isNullOrEmpty(request.getParameter("group_name"))) {
            this.groupName = request.getParameter("group_name");
        } else if(!Strings.isNullOrEmpty(request.getParameter("project_name"))) {
            this.projectName = request.getParameter("project_name");
        }
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "{" +
                "page=" + page +
                ", userId=" + userId +
                ", code=" + code +
                ", alarmName='" + alarmName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
