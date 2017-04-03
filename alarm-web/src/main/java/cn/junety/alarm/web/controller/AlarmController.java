package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.AlarmService;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.vo.AlarmForm;
import cn.junety.alarm.web.vo.AlarmVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/3/24.
 */
@Controller
public class AlarmController extends BaseController {

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
    public ModelAndView toAlarmPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /alarm, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("alarm");
    }

    @ResponseBody
    @RequestMapping(value = "/alarms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarmList(HttpServletRequest request) {
        User user = getUser(request);
        try {
            AlarmForm alarmForm = new AlarmForm(request);
            logger.info("GET /alarms, user:{}, form:{}", JSON.toJSONString(user), JSON.toJSONString(alarmForm));

            List<AlarmVO> alarms = alarmService.getAlarmInfo(user, alarmForm);
            int count = alarmService.getAlarmInfoCount(user, alarmForm);

            return ResponseHelper.buildResponse(2000, "alarms", alarms, "count", count);
        } catch (Exception e) {
            logger.error("get alarm list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "alarms", Collections.emptyList(), "count", 0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/alarms/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCreateInfo(HttpServletRequest request) {
        User user = getUser(request);
        logger.info("GET /alarms/info, user:{}", JSON.toJSONString(user));

        List<Integer> codes = alarmService.getAlarmCodeList(user);
        List<Project> projects = projectService.getProjectList();
        List<Module> modules;
        if (projects.size() > 0) {
            modules = projectService.getModuleByProjectId(projects.get(0).getId());
        } else {
            modules = Collections.emptyList();
        }
        List<Group> groups = groupService.getAllGroup();

        return ResponseHelper.buildResponse(2000, "codes", codes, "projects", projects,
                "modules", modules, "groups", groups);
    }

    @ResponseBody
    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAlarmById(HttpServletRequest request, @PathVariable Integer aid) {
        User user = getUser(request);
        logger.info("GET /alarms/{}, user:{}", aid, JSON.toJSONString(user));

        List<Integer> codes = alarmService.getAlarmCodeList(user);
        List<Project> projects = projectService.getProjectList();
        List<Module> modules;
        if (projects.size() > 0) {
            modules = projectService.getModuleByProjectId(projects.get(0).getId());
        } else {
            modules = Collections.emptyList();
        }
        List<Group> groups = groupService.getAllGroup();
        Alarm alarm = alarmService.getAlarmById(aid);

        return ResponseHelper.buildResponse(2000, "codes", codes, "projects", projects,
                "modules", modules, "groups", groups, "alarm", alarm);
    }

    @ResponseBody
    @RequestMapping(value = "/alarms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createAlarm(HttpServletRequest request, @RequestBody Alarm alarm) {
        User user = getUser(request);
        logger.info("POST /alarms, user:{}, alarm:{}", JSON.toJSONString(user), JSON.toJSONString(alarm));

        try {
            alarmService.createAlarm(alarm);
            return ResponseHelper.buildResponse(2000);
        } catch (Exception e) {
            logger.error("create alarm error, alarm:{}, caused by", JSON.toJSONString(alarm), e);
            return ResponseHelper.buildResponse(5000, "reason", e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/alarms", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAlarm(HttpServletRequest request, @RequestBody Alarm alarm) {
        User user = getUser(request);
        logger.info("PUT /alarms, user:{}, alarm:{}", JSON.toJSONString(user), JSON.toJSONString(alarm));

        try {
            alarmService.updateAlarm(alarm);
            return ResponseHelper.buildResponse(2000);
        } catch (Exception e) {
            logger.error("update alarm error, alarm:{}, caused by", JSON.toJSONString(alarm), e);
            return ResponseHelper.buildResponse(5000, "reason", e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/alarms/{aid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAlarm(HttpServletRequest request, @PathVariable Integer aid) {
        User user = getUser(request);
        logger.info("DELETE /alarms/{}, user:{}", aid, JSON.toJSONString(user));

        alarmService.deleteAlarmById(aid);

        return ResponseHelper.buildResponse(2000);
    }
}
