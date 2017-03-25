package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.ResponseHelper;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@ControllerAdvice
public class BaseController {

    @ExceptionHandler(HttpMessageConversionException.class)
    public String invalidParameterHandler(HttpServletRequest req, Exception e) {
        return ResponseHelper.buildResponse(4000, "invalid params");
    }

    @ExceptionHandler(Exception.class)
    public String badService(Exception e) {
        return ResponseHelper.buildResponse(5000, "bad service");
    }
}