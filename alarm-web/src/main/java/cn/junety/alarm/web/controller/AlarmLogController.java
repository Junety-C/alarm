package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.AlarmLogService;
import cn.junety.alarm.web.vo.AlarmLogSearch;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class AlarmLogController extends BaseController {

    @Autowired
    private AlarmLogService alarmLogService;

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogs(HttpServletRequest request) {
        User user = getUser(request);
        try {
            AlarmLogSearch alarmLogSearch = new AlarmLogSearch(request, user);
            logger.info("GET /logs, user:{}, search:{}", JSON.toJSONString(user), JSON.toJSONString(alarmLogSearch));

            List<AlarmLog> logs = alarmLogService.getLogList(alarmLogSearch);
            int count = alarmLogService.getLogCount(alarmLogSearch);

            return ResponseHelper.buildResponse(2000, "logs", logs, "count", count);
        } catch (Exception e) {
            logger.error("get log list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "alarms", Collections.emptyList(), "count", 0);
        }
    }
}
