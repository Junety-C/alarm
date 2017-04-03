package cn.junety.alarm.web.vo;

import cn.junety.alarm.base.entity.User;
import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/27.
 */
public class GroupSearch {
    private Page page;
    private Integer userId;
    private String groupName;

    public GroupSearch(HttpServletRequest request, User user) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);
        this.userId = user.getId();

        if(!Strings.isNullOrEmpty(request.getParameter("group_name"))) {
            this.groupName = request.getParameter("group_name");
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
