package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.User;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/4/3.
 */
@Controller
public class PageController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLoginPage() {
        logger.info("GET /login");
        return "login";
    }

    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
    public ModelAndView toAlarmPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /alarm, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("alarm");
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView toProjectPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /project, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("project-module");
    }
}
