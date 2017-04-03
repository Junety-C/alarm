package cn.junety.alarm.web.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by caijt on 2017/4/2.
 * 用户登录状态相关
 */
@Service
public class UserLoginStatusService {

    public final static String LOGIN_USER_STATUS_NAME = "ALARM_CENTER_USER_STATUS";

    public String getIdentificationFromLoginStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Object object = session.getAttribute(LOGIN_USER_STATUS_NAME);
        return object == null ? "" : object.toString();
    }

    public void addLoginStatus(HttpServletRequest request, String identification) {
        request.getSession().setAttribute(LOGIN_USER_STATUS_NAME, identification);
    }

    public void removeLoginStatus(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_USER_STATUS_NAME);
    }
}
