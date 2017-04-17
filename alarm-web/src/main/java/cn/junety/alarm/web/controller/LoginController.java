package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.vo.LoginForm;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/4/2.
 * 登录/注销API
 */
@RestController
public class LoginController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @RequestBody LoginForm loginForm) {
        logger.info("POST /login, loginForm:{}", JSON.toJSONString(loginForm));

        User user = userService.getUserByAccount(loginForm.getUsername());
        if (user != null) {
            // TODO 接入到运维系统，进行密码校验
            userLoginStatusService.addLoginStatus(request, user.getIdentification());
            return ResponseHelper.buildResponse(2000, "status", "success");
        }
        return ResponseHelper.buildResponse(4004, "reason", "invalid username or password");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request) {
        logger.info("POST /logout");
        userLoginStatusService.removeLoginStatus(request);
        return ResponseHelper.buildResponse(2000);
    }
}
