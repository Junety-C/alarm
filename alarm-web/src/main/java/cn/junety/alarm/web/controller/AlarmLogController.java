package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.AlarmLogService;
import cn.junety.alarm.web.vo.AlarmLogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/3/24.
 * 告警日志API
 */
@RestController
public class AlarmLogController extends BaseController {

    @Autowired
    private AlarmLogService alarmLogService;

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogList(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            AlarmLogSearch alarmLogSearch = new AlarmLogSearch(request, currentUser);
            logger.info("GET /logs, current_user:{}, search:{}", currentUser, alarmLogSearch);

            List<AlarmLog> logList = alarmLogService.getLogList(alarmLogSearch);
            int logCount = alarmLogService.getLogCount(alarmLogSearch);

            return ResponseHelper.buildResponse(2000, "log_list", logList, "log_count", logCount);
        } catch (Exception e) {
            logger.error("get log list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "log_list", Collections.emptyList(), "log_count", 0);
        }
    }
}
