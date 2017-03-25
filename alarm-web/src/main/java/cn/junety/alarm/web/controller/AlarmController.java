package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class AlarmController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @RequestMapping(value = "/alarms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarms(HttpServletRequest request) {
        logger.info("GET /alarms, body:{}");
        return ResponseHelper.buildResponse(2000, "GET /alarms");
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarmById(HttpServletRequest request, @PathVariable Integer aid) {
        logger.info("GET /alarms/{}, body:{}", aid);
        return ResponseHelper.buildResponse(2000, "GET /alarms/"+aid);
    }

    @RequestMapping(value = "/alarms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addAlarm(HttpServletRequest request) {
        logger.info("POST /alarms, body:{}");
        return ResponseHelper.buildResponse(2000, "POST /alarms");
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        logger.info("PUT /alarms/{}, body:{}", aid);
        return ResponseHelper.buildResponse(2000, "PUT /alarms/"+aid);
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        logger.info("DELETE /alarms/{}, body:{}", aid);
        return ResponseHelper.buildResponse(2000, "DELETE /alarms/"+aid);
    }
}
