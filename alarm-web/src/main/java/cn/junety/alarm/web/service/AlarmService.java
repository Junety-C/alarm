package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.dao.AlarmDao;
import cn.junety.alarm.web.dao.GroupDao;
import cn.junety.alarm.web.dao.ModuleDao;
import cn.junety.alarm.web.dao.ProjectDao;
import cn.junety.alarm.web.vo.AlarmSearch;
import cn.junety.alarm.web.vo.AlarmVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 * 处理告警
 */
@Service
public class AlarmService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

    @Autowired
    private AlarmDao alarmDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private GroupDao groupDao;

    /**
     * 获取告警列表
     * @param user 用户信息
     * @param alarmSearch 查询参数
     * @return 告警信息列表
     */
    public List<AlarmVO> getAlarmInfo(User user, AlarmSearch alarmSearch) {
        List<Alarm> alarms;

        // 管理员获取所有告警, 普通用户获取自己能接收到的告警
        if (UserTypeEnum.ADMIN_USER.value().equals(user.getType())) {
            logger.debug("get all alarm info, user:{}", user);
            alarms = getAllAlarm(alarmSearch);
        } else {
            logger.debug("get user alarm info, user:{}", user);
            alarms = getUserAlarm(alarmSearch);
        }

        List<AlarmVO> results = new ArrayList<>();
        for(Alarm alarm : alarms) {
            AlarmVO alarmVO = new AlarmVO();
            alarmVO.setAlarm(alarm);
            alarmVO.setProject(projectDao.getProjectById(alarm.getProjectId()));
            alarmVO.setModule(moduleDao.getModuleById(alarm.getModuleId()));
            alarmVO.setGroup(groupDao.getGroupById(alarm.getGroupId()));
            results.add(alarmVO);
        }
        return results;
    }

    private List<Alarm> getAllAlarm(AlarmSearch alarmSearch) {
        if(alarmSearch.getCode() != null) {
            return alarmDao.getAlarmByCode(alarmSearch);
        } else if(alarmSearch.getAlarmName() != null) {
            return alarmDao.getAlarmByName(alarmSearch);
        } else if(alarmSearch.getGroupName() != null){
            return alarmDao.getAlarmByGroupName(alarmSearch);
        } else if(alarmSearch.getProjectName() != null) {
            return alarmDao.getAlarmByProjectName(alarmSearch);
        } else {
            return alarmDao.getAlarm(alarmSearch);
        }
    }

    private List<Alarm> getUserAlarm(AlarmSearch alarmSearch) {
        if(alarmSearch.getCode() != null) {
            return alarmDao.getUserAlarmByCode(alarmSearch);
        } else if(alarmSearch.getAlarmName() != null) {
            return alarmDao.getUserAlarmByName(alarmSearch);
        } else if(alarmSearch.getGroupName() != null){
            return alarmDao.getUserAlarmByGroupName(alarmSearch);
        } else if(alarmSearch.getProjectName() != null) {
            return alarmDao.getUserAlarmByProjectName(alarmSearch);
        } else {
            return alarmDao.getUserAlarm(alarmSearch);
        }
    }

    /**
     * 获取告警列表的长度，用于分页
     * @param user 用户信息
     * @param alarmSearch 查询参数
     * @return 告警列表长度
     */
    public int getAlarmInfoCount(User user, AlarmSearch alarmSearch) {
        // 管理员获取所有告警, 普通用户获取自己能接收到的告警
        if (UserTypeEnum.ADMIN_USER.value().equals(user.getType())) {
            logger.debug("get all alarm info count, user:{}", JSON.toJSONString(user));
            return getAllAlarmCount(alarmSearch);
        } else {
            logger.debug("get user alarm info count, user:{}", JSON.toJSONString(user));
            return getUserAlarmCount(alarmSearch);
        }
    }

    private int getAllAlarmCount(AlarmSearch alarmSearch) {
        if(alarmSearch.getCode() != null) {
            return alarmDao.getAlarmCountByCode(alarmSearch);
        } else if(alarmSearch.getAlarmName() != null) {
            return alarmDao.getAlarmCountByName(alarmSearch);
        } else if(alarmSearch.getGroupName() != null){
            return alarmDao.getAlarmCountByGroupName(alarmSearch);
        } else if(alarmSearch.getProjectName() != null) {
            return alarmDao.getAlarmCountByProjectName(alarmSearch);
        } else {
            return alarmDao.getAlarmCount();
        }
    }

    private int getUserAlarmCount(AlarmSearch alarmSearch) {
        if(alarmSearch.getCode() != null) {
            return alarmDao.getUserAlarmCountByCode(alarmSearch);
        } else if(alarmSearch.getAlarmName() != null) {
            return alarmDao.getUserAlarmCountByName(alarmSearch);
        } else if(alarmSearch.getGroupName() != null){
            return alarmDao.getUserAlarmCountByGroupName(alarmSearch);
        } else if(alarmSearch.getProjectName() != null) {
            return alarmDao.getUserAlarmCountByProjectName(alarmSearch);
        } else {
            return alarmDao.getUserAlarmCount(alarmSearch);
        }
    }

    /**
     * 根据id获取告警
     * @param aid 告警id
     * @return 告警信息
     */
    public Alarm getAlarmById(Integer aid) {
        return alarmDao.getAlarmById(aid);
    }

    /**
     * 创建告警
     * @param alarm 告警信息
     */
    public void createAlarm(Alarm alarm) {
        alarmDao.save(alarm);
    }

    /**
     * 根据id删除告警
     * @param aid 告警id
     */
    public void deleteAlarmById(Integer aid) {
        alarmDao.deleteById(aid);
    }

    /**
     * 更新告警信息
     * @param alarm 告警信息
     */
    public void updateAlarm(Alarm alarm) {
        alarmDao.updateById(alarm);
    }
}
