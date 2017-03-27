package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.AlarmLogService;
import cn.junety.alarm.web.vo.AlarmLogForm;
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
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class AlarmLogController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmLogController.class);

    @Autowired
    private AlarmLogService alarmLogService;

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogs(HttpServletRequest request) {
        AlarmLogForm alarmLogForm = new AlarmLogForm(request);
        logger.info("GET /logs, body:{}", JSON.toJSONString(alarmLogForm));
        List<AlarmLog> logs = alarmLogService.getLogs(alarmLogForm);
        Map<String, Object> results = new HashMap<>();
        results.put("logs", logs);
        results.put("count", alarmLogService.getLogCount(alarmLogForm));
        return ResponseHelper.buildResponse(2000, results);
    }
}
