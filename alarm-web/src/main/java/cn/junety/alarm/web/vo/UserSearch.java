package cn.junety.alarm.web.vo;

import cn.junety.alarm.base.entity.User;
import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/4/16.
 */
public class UserSearch {
    private Page page;
    private Integer userId;
    private Integer userType;
    private String name;
    private String account;

    public UserSearch(HttpServletRequest request, User user) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);
        this.userId = user.getId();
        this.userType = user.getType();

        if(!Strings.isNullOrEmpty(request.getParameter("name"))) {
            this.name = request.getParameter("name");
        } else if(!Strings.isNullOrEmpty(request.getParameter("account"))) {
            this.account = request.getParameter("account");
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "{" +
                "page=" + page +
                ", userId=" + userId +
                ", userType=" + userType +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
