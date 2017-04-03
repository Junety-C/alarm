package cn.junety.alarm.web.vo;

import cn.junety.alarm.base.entity.User;
import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/27.
 */
public class AlarmLogSearch {
    private Page page;
    private Integer userId;
    private Integer code;
    private String alarmName;
    private String projectName;

    public AlarmLogSearch(HttpServletRequest request, User user) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);
        this.userId = user.getId();

        if(!Strings.isNullOrEmpty(request.getParameter("code"))) {
            this.code = Integer.valueOf(request.getParameter("code").trim());
        } else if(!Strings.isNullOrEmpty(request.getParameter("alarm_name"))) {
            this.alarmName = request.getParameter("alarm_name").trim();
        } else if(!Strings.isNullOrEmpty(request.getParameter("project"))) {
            this.projectName = request.getParameter("project").trim();
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
}
