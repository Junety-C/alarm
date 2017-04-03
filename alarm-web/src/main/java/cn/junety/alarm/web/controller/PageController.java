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
    public ModelAndView toLoginPage() {
        logger.info("GET /login");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getAlarms(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /index, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("home");
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

    @RequestMapping(value = "/receiver", method = RequestMethod.GET)
    public ModelAndView toReceiverPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /receiver, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("receiver");
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public ModelAndView toGroupPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /group, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("group");
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ModelAndView toLogPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /log, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("log");
    }
}
