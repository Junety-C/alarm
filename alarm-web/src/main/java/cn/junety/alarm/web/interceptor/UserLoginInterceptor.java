package cn.junety.alarm.web.interceptor;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.base.entity.UserTypeEnum;
import cn.junety.alarm.web.service.UserLoginStatusService;
import cn.junety.alarm.web.service.UserService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caijt on 2017/4/2.
 *
 * 对未登陆的使用者进行拦截
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginStatusService userLoginStatusService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String identification = userLoginStatusService.getIdentificationFromLoginStatus(request);
        User user = userService.getUserByIdentification(identification);

        if (user == null) {
            logger.debug("login status check fail, identification:{}", identification);
            response.sendRedirect("/login");
            return false;
        }

        request.setAttribute("user", user);
        return true;
    }
}
