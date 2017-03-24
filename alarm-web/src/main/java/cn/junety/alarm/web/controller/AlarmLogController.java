package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
@RequestMapping
public class AlarmLogController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmLogController.class);

    @RequestMapping(value = "/logs", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLogs(HttpServletRequest request) {
        logger.info("GET /logs, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }
}
