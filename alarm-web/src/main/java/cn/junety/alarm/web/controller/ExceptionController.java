package cn.junety.alarm.web.controller;

import cn.junety.alarm.api.AlarmClient;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 * 异常处理
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(HttpMessageConversionException.class)
    public String invalidParameterHandler(HttpServletRequest request, Exception e) {
        User user = (User) request.getAttribute("user");
        logger.error("invalid parameter error, user:{}", JSON.toJSONString(user), e);
        return ResponseHelper.buildResponse(4000, "reason", "invalid params");
    }

    @ExceptionHandler(Exception.class)
    public String badService(HttpServletRequest request, Exception e) {
        User user = (User) request.getAttribute("user");
        logger.error("bad service, user:{}", JSON.toJSONString(user), e);
        AlarmClient.error(1, "alarm.web.bad-service", "告警web端异常,error:" + e.getMessage());
        return ResponseHelper.buildResponse(5000, "reason", "bad service");
    }
}