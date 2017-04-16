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
        User currentUser = getUser(request);
        logger.info("GET /home, current_user:{}", currentUser);

        model.addAttribute("current_user", currentUser);

        return new ModelAndView("home");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView toUserPage(HttpServletRequest request, Model model) {
        User currentUser = getUser(request);
        logger.info("GET /user, current_user:{}", currentUser);

        model.addAttribute("current_user", currentUser);

        return new ModelAndView("user");
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView toProjectPage(HttpServletRequest request, Model model) {
        User currentUser = getUser(request);
        logger.info("GET /project, current_user:{}", currentUser);

        model.addAttribute("current_user", currentUser);

        return new ModelAndView("project");
    }

    @RequestMapping(value = "/module", method = RequestMethod.GET)
    public ModelAndView toModulePage(HttpServletRequest request, Model model) {
        User currentUser = getUser(request);
        logger.info("GET /module, current_user:{}", currentUser);

        model.addAttribute("current_user", currentUser);

        return new ModelAndView("module");
    }

    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public ModelAndView toProjectMemberPage(HttpServletRequest request, Model model) {
        User currentUser = getUser(request);
        logger.info("GET /member, current_user:{}", currentUser);

        model.addAttribute("current_user", currentUser);

        return new ModelAndView("member");
    }

    // =========================


    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
    public ModelAndView toAlarmPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /alarm, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("alarm");
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
