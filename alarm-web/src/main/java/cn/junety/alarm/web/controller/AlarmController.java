package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Alarm;
import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.AlarmService;
import cn.junety.alarm.web.vo.AlarmForm;
import cn.junety.alarm.web.vo.AlarmVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class AlarmController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private AlarmService alarmService;

    @RequestMapping(value = "/alarms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarms(HttpServletRequest request) {
        String reqId = request.getSession().getId();
        AlarmForm alarmForm = new AlarmForm(request);
        logger.info("reqId:{}, GET /alarms, body:{}", reqId, JSON.toJSONString(alarmForm));
        List<AlarmVO> alarms = alarmService.getAlarmInfo(alarmForm);
        Map<String, Object> results = new HashMap<>();
        results.put("alarms", alarms);
        results.put("count", alarmService.getAlarmInfoCount(alarmForm));
        return ResponseHelper.buildResponse(2000, reqId, results);
    }

    @RequestMapping(value = "/alarms/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCreateInfo(HttpServletRequest request) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, GET /alarms/info", reqId);
        Map<String, Object> results = new HashMap<>();
        results.put("codes", alarmService.getCodes());
        List<Project> projects = alarmService.getProjects();
        results.put("projects", projects);
        results.put("modules", alarmService.getModules(projects.get(0).getId()));
        results.put("groups", alarmService.getGroups());
        return ResponseHelper.buildResponse(2000, reqId, results);
    }

    // 获取指定id的告警
    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarmById(HttpServletRequest request, @PathVariable Integer aid) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, GET /alarms/{}", reqId, aid);
        Map<String, Object> results = new HashMap<>();
        results.put("codes", alarmService.getCodes());
        List<Project> projects = alarmService.getProjects();
        Alarm alarm = alarmService.getAlarmById(aid);
        results.put("projects", projects);
        results.put("modules", alarmService.getModules(alarm.getProjectId()));
        results.put("groups", alarmService.getGroups());
        results.put("alarm", alarm);
        return ResponseHelper.buildResponse(2000, reqId, results);
    }

    @RequestMapping(value = "/alarms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addAlarm(HttpServletRequest request, @RequestBody Alarm alarm) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, POST /alarms, body:{}", reqId, alarm);
        alarmService.createAlarm(alarm);
        return ResponseHelper.buildResponse(2000, reqId, "success");
    }

    @RequestMapping(value = "/alarms", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAlarm(HttpServletRequest request, @RequestBody Alarm alarm) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, PUT /alarms, body:{}", reqId, JSON.toJSONString(alarm));
        alarmService.updateAlarm(alarm);
        return ResponseHelper.buildResponse(2000, reqId, "success");
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, DELETE /alarms/{}", reqId, aid);
        alarmService.deleteAlarmById(aid);
        return ResponseHelper.buildResponse(2000, reqId, "success");
    }
}
