package cn.junety.alarm.web.vo;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/27.
 */
public class GroupForm {
    private Integer page;
    private Integer length;
    private String name;

    public GroupForm(HttpServletRequest request) {
        this.page = Integer.valueOf(request.getParameter("page"));
        this.length = Integer.valueOf(request.getParameter("length"));
        if(!Strings.isNullOrEmpty(request.getParameter("name"))) {
            this.name = request.getParameter("name").trim();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
