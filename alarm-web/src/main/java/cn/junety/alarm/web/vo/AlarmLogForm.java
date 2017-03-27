package cn.junety.alarm.web.vo;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/27.
 */
public class AlarmLogForm {
    private Integer page;
    private Integer length;
    private Integer code;
    private String alarmName;
    private String projectName;

    public AlarmLogForm(HttpServletRequest request) {
        this.page = Integer.valueOf(request.getParameter("page"));
        this.length = Integer.valueOf(request.getParameter("length"));
        if(!Strings.isNullOrEmpty(request.getParameter("code"))) {
            this.code = Integer.valueOf(request.getParameter("code").trim());
        } else if(!Strings.isNullOrEmpty(request.getParameter("alarm_name"))) {
            this.alarmName = request.getParameter("alarm_name").trim();
        } else if(!Strings.isNullOrEmpty(request.getParameter("project"))) {
            this.projectName = request.getParameter("project").trim();
        }
    }

    public Integer getPage() {
        if (page == null || page < 1) {
            return 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLength() {
        if (length == null) {
            return 10;
        }
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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
}
