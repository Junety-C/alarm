package cn.junety.alarm.server.controller;

import cn.junety.alarm.api.AlarmClient;
import cn.junety.alarm.server.common.HttpHelper;
import cn.junety.alarm.server.service.AlarmService;
import cn.junety.alarm.server.vo.AlarmForm;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/1/28.
 */
@RestController
@RequestMapping("/v1")
public class AlarmController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private AlarmService alarmService;

    @RequestMapping(value = "/alarm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String report(HttpServletRequest request, @RequestBody AlarmForm alarmForm) {
        alarmForm.setIp(HttpHelper.getRemoteIp(request));
        logger.info("POST /v1/alarm, body:{}.", JSON.toJSONString(alarmForm));

        try {
            if(alarmForm.validate()) {
                return alarmService.handle(alarmForm);
            }
            logger.debug("invalid params, body:{}.", JSON.toJSONString(alarmForm));
            return HttpHelper.buildResponse(4000, "invalid params");
        } catch (Exception e) {
            logger.error("bad service", e);
            AlarmClient.error(1, "alarm.server.bad-service", "告警处理异常,error:" + e.getMessage());
            return HttpHelper.buildResponse(5000, "bad service");
        }
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public String invalidParameterHandler(HttpServletRequest req, Exception e) {
        return HttpHelper.buildResponse(4000, "invalid params");
    }

    @ExceptionHandler(Exception.class)
    public String badService(Exception e) {
        return HttpHelper.buildResponse(5000, "bad service");
    }
}
