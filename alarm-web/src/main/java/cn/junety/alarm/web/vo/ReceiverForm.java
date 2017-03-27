package cn.junety.alarm.web.vo;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/27.
 */
public class ReceiverForm {
    private Integer page;
    private Integer length;
    private String name;
    private String phone;
    private String mail;
    private String wechat;
    private String qq;

    public ReceiverForm(HttpServletRequest request) {
        this.page = Integer.valueOf(request.getParameter("page"));
        this.length = Integer.valueOf(request.getParameter("length"));
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
