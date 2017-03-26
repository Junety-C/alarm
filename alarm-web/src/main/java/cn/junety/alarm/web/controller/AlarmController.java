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
        AlarmForm alarmForm = new AlarmForm(request);
        logger.info("GET /alarms, body:{}", JSON.toJSONString(alarmForm));
        List<AlarmVO> alarmList = alarmService.getAlarmInfo(alarmForm);
        Map<String, Object> results = new HashMap<>();
        results.put("alarms", alarmList);
        results.put("count", alarmService.getAlarmInfoCount(alarmForm));
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/alarms/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCreateInfo() {
        logger.info("GET /alarms/info");
        Map<String, Object> results = new HashMap<>();
        results.put("codes", alarmService.getCodes());
        List<Project> projects = alarmService.getProjects();
        results.put("projects", projects);
        results.put("modules", alarmService.getModules(projects.get(0).getId()));
        results.put("groups", alarmService.getGroups());
        return ResponseHelper.buildResponse(2000, results);
    }

    // 获取指定id的告警
    //@RequestMapping(value = "/alarms/{aid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    //public String getAlarmById(HttpServletRequest request, @PathVariable Integer aid) {
    //    logger.info("GET /alarms/{}, body:{}", aid);
    //    return ResponseHelper.buildResponse(2000, "GET /alarms/"+aid);
    //}

    @RequestMapping(value = "/alarms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addAlarm(@RequestBody Alarm alarm) {
        logger.info("POST /alarms, body:{}", alarm);
        alarmService.createAlarm(alarm);
        return ResponseHelper.buildResponse(2000, "success");
    }

    // 更新指定id告警
    //@RequestMapping(value = "/alarms/{aid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    //public String updateAlarm(HttpServletRequest request, @PathVariable Integer aid) {
    //    logger.info("PUT /alarms/{}, body:{}", aid);
    //    return ResponseHelper.buildResponse(2000, "PUT /alarms/"+aid);
    //}

    // 删除指定id告警
    //@RequestMapping(value = "/alarms/{aid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    //public String deleteAlarm(HttpServletRequest request, @PathVariable Integer aid) {
    //    logger.info("DELETE /alarms/{}, body:{}", aid);
    //    return ResponseHelper.buildResponse(2000, "DELETE /alarms/"+aid);
    //}
}
