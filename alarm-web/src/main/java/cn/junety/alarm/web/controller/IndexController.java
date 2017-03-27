package cn.junety.alarm.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/25.
 */
@Controller
@RequestMapping
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getAlarms(HttpServletRequest request) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, GET /index, body:{}", reqId, request.getSession().getId());
        return "home";
    }
}
