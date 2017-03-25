package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class AlarmLogController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmLogController.class);

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogs(HttpServletRequest request) {
        logger.info("GET /logs, body:{}");
        return ResponseHelper.buildResponse(2000, "GET /logs");
    }
}
