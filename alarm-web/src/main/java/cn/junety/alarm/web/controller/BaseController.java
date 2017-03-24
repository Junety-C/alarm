package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.HttpHelper;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> invalidParameterHandler(HttpServletRequest req, Exception e) {
        return HttpHelper.buildResponse(400, 4000, "invalid params");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> badService(Exception e) {
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }
}