package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.service.MonitorService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by caijt on 2017/4/5.
 * 系统监控API
 */
@RestController
@RequestMapping("monitor")
public class MonitorController {

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private MonitorService monitorService;

    @RequestMapping(value = "/queues/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getQueueStatus(HttpServletRequest request) {
        Map<String, Long> queueStatus = monitorService.getQueueStatus();
        return JSON.toJSONString(queueStatus);
    }

    @RequestMapping(value = "/request/quantity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getServerThroughput(HttpServletRequest request) {
        int quantity = monitorService.getTotalRequestQuantity();
        return "{\"quantity\":" + quantity + "}";
    }

    @RequestMapping(value = "/sending/quantity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPushQuantity(HttpServletRequest request) {
        Map<String, String> sendingQuantity = monitorService.getPushQuantity();
        return JSON.toJSONString(sendingQuantity);
    }
}
