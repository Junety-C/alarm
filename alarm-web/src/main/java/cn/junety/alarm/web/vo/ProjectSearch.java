package cn.junety.alarm.web.vo;

import cn.junety.alarm.base.entity.User;
import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/26.
 */
public class ProjectSearch {
    private Page page;
    private Integer userId;
    private String projectName;

    public ProjectSearch(HttpServletRequest request, User user) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);
        this.userId = user.getId();

        if(!Strings.isNullOrEmpty(request.getParameter("project_name"))) {
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
