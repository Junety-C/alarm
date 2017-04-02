package cn.junety.alarm.web.controller;

import cn.junety.alarm.api.AlarmClient;
import cn.junety.alarm.web.common.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(HttpMessageConversionException.class)
    public String invalidParameterHandler(HttpServletRequest request, Exception e) {
        logger.error("{}", e);
        return ResponseHelper.buildResponse(4000, "invalid params");
    }

    @ExceptionHandler(Exception.class)
    public String badService(HttpServletRequest request, Exception e) {
        logger.error("{}", e);
        AlarmClient.error(1, "alarm.web.bad-service", "告警web端异常,error:" + e.getMessage());
        return ResponseHelper.buildResponse(5000, "bad service");
    }
}