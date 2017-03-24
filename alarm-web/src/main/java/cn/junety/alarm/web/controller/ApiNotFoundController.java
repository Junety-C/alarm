package cn.junety.alarm.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class ApiNotFoundController {

    @ResponseBody
    @RequestMapping(value = "/**", produces = {"application/json;charset=UTF-8"})
    public String apiNotFound(HttpServletRequest request) {
        return "{\"error\":\"api not found\"}";
    }
}
