package cn.junety.alarm.web.vo;

import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/27.
 */
public class ReceiverSearch {
    private Page page;
    private String name;
    private String phone;
    private String mail;
    private String wechat;
    private String qq;

    public ReceiverSearch(HttpServletRequest request) {
        int pageNo = NumberUtils.toInt(request.getParameter("page_no"), 1);
        int pageSize = NumberUtils.toInt(request.getParameter("page_size"), 10);
        this.page = new Page(pageNo, pageSize);

        if(!Strings.isNullOrEmpty(request.getParameter("name"))) {
            this.name = request.getParameter("name").trim();
        } else if(!Strings.isNullOrEmpty(request.getParameter("phone"))) {
            this.phone = request.getParameter("phone").trim();
        } else if(!Strings.isNullOrEmpty(request.getParameter("mail"))) {
            this.mail = request.getParameter("mail").trim();
        } else if(!Strings.isNullOrEmpty(request.getParameter("wechat"))) {
            this.wechat = request.getParameter("wechat").trim();
        } else if(!Strings.isNullOrEmpty(request.getParameter("qq"))) {
            this.qq = request.getParameter("qq").trim();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
