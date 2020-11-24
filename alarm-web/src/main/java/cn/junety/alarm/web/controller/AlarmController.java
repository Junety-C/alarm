package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.AlarmService;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.service.ModuleService;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.vo.AlarmSearch;
import cn.junety.alarm.web.vo.AlarmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/3/24.
 * 告警API
 */
@RestController
public class AlarmController extends BaseController {

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/alarms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarmList(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            AlarmSearch alarmSearch = new AlarmSearch(request, currentUser);
            logger.info("GET /alarms, current_user:{}, search:{}", currentUser, alarmSearch);

            List<AlarmVO> alarmList = alarmService.getAlarmInfo(currentUser, alarmSearch);
            int alarmCount = alarmService.getAlarmInfoCount(currentUser, alarmSearch);

            return ResponseHelper.buildResponse(2000, "alarm_list", alarmList, "alarm_count", alarmCount);
        } catch (Exception e) {
            logger.error("get alarm list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "alarm_list", Collections.emptyList(), "alarm_count", 0);
        }
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarmById(HttpServletRequest request, @PathVariable Integer aid) {
        User currentUser = getUser(request);
        logger.info("GET /alarms/{}, current_user:{}", aid, currentUser);

        Alarm alarm = alarmService.getAlarmById(aid);
        List<Project> projectList = projectService.getProjectList(currentUser);
        List<cn.junety.alarm.base.entity.Module> moduleList = moduleService.getModuleList(alarm.getProjectId());
        List<Group> groupList = groupService.getGroupList(alarm.getProjectId());

        return ResponseHelper.buildResponse(2000, "project_list", projectList,
                "module_list", moduleList, "group_list", groupList, "alarm", alarm);
    }

    @RequestMapping(value = "/alarms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createAlarm(HttpServletRequest request, @RequestBody Alarm alarm) {
        User currentUser = getUser(request);
        logger.info("POST /alarms, current_user:{}, alarm:{}", currentUser, alarm);

        try {
            alarmService.createAlarm(alarm);
            return ResponseHelper.buildResponse(2000);
        } catch (Exception e) {
            logger.error("create alarm error, alarm:{}, caused by", alarm, e);
            return ResponseHelper.buildResponse(5000, "reason", e.getMessage());
        }
    }

    @RequestMapping(value = "/alarms", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAlarm(HttpServletRequest request, @RequestBody Alarm alarm) {
        User currentUser = getUser(request);
        logger.info("PUT /alarms, current_user:{}, alarm:{}", currentUser, alarm);

        try {
            alarmService.updateAlarm(alarm);
            return ResponseHelper.buildResponse(2000);
        } catch (Exception e) {
            logger.error("update alarm error, alarm:{}, caused by", alarm, e);
            return ResponseHelper.buildResponse(5000, "reason", e.getMessage());
        }
    }

    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        User currentUser = getUser(request);
        logger.info("DELETE /alarms/{}, current_user:{}", aid, currentUser);

        alarmService.deleteAlarmById(aid);

        return ResponseHelper.buildResponse(2000);
    }
}
