package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by caijt on 2017/4/2.
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserService userService;
}
