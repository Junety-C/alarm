package cn.junety.alarm.web.vo;

import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/4/16.
 */
public class UserSearch {
    private Page page;
    private String name;
    private String account;

    public UserSearch(HttpServletRequest request) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);

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
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
