package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.HttpHelper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
@RequestMapping
public class AlarmController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @RequestMapping(value = "/alarms", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAlarms(HttpServletRequest request) {
        logger.info("GET /alarms, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAlarmById(HttpServletRequest request, @PathVariable Integer aid) {
        logger.info("GET /alarms/{}, body:{}", aid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/alarms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAlarm(HttpServletRequest request) {
        logger.info("POST /alarms, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        logger.info("PUT /alarms/{}, body:{}", aid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        logger.info("DELETE /alarms/{}, body:{}", aid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }
}
