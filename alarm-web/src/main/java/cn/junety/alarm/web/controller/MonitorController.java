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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by caijt on 2017/4/5.
 */
@RestController
@RequestMapping("monitor")
public class MonitorController {

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private MonitorService monitorService;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @RequestMapping(value = "/queues/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getQueueStatus(HttpServletRequest request) {
        //Map<String, Long> queueStatus = monitorService.getQueueStatus();
        Map<String, Long> queueStatus = new HashMap<>();
        Random random = new Random();
        queueStatus.put("mail", (long)random.nextInt(100));
        queueStatus.put("sms", (long)random.nextInt(100));
        queueStatus.put("qq", (long)random.nextInt(100));
        queueStatus.put("wechat", (long)random.nextInt(100));
        return JSON.toJSONString(queueStatus);
    }

    @RequestMapping(value = "/request/quantity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getServerThroughput(HttpServletRequest request) {
        //int quantity = monitorService.getTotalRequestQuantity();
        Random random = new Random();
        int quantity = random.nextInt(100);
        return "{\"quantity\":" + atomicInteger.addAndGet(quantity) + "}";
    }
}
