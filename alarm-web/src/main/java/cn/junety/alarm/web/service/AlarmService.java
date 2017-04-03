package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.AlarmDao;
import cn.junety.alarm.base.dao.GroupDao;
import cn.junety.alarm.base.dao.ModuleDao;
import cn.junety.alarm.base.dao.ProjectDao;
import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.vo.AlarmForm;
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
     * @param user
     * @param alarmForm
     * @return
     */
    public List<AlarmVO> getAlarmInfo(User user, AlarmForm alarmForm) {
        List<Alarm> alarms;

        // 管理员获取所有告警, 普通用户获取自己能接收到的告警
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            alarms = getAllAlarm(alarmForm);
            logger.debug("get all alarm info, user:{}", JSON.toJSONString(user));
        } else {
            alarms = getUserAlarm(user, alarmForm);
            logger.debug("get user alarm info, user:{}", JSON.toJSONString(user));
        }

        List<AlarmVO> results = new ArrayList<>();
        for(Alarm alarm : alarms) {
            AlarmVO alarmVO = new AlarmVO();
            alarmVO.setAlarm(alarm);
            alarmVO.setProject(projectDao.getById(alarm.getProjectId()));
            alarmVO.setModule(moduleDao.getById(alarm.getModuleId()));
            alarmVO.setGroup(groupDao.getById(alarm.getGroupId()));
            results.add(alarmVO);
        }
        return results;
    }

    private List<Alarm> getAllAlarm(AlarmForm alarmForm) {
        // 分页参数
        int length = alarmForm.getLength();
        int begin =  (alarmForm.getPage() - 1) * length;

        if(alarmForm.getCode() != null) {
            return alarmDao.getAlarmByCode(alarmForm.getCode(), begin, length);
        } else if(alarmForm.getAlarmName() != null) {
            return alarmDao.getAlarmByName(alarmForm.getAlarmName()+"%", begin, length);
        } else if(alarmForm.getGroupName() != null){
            return alarmDao.getAlarmByGroupName(alarmForm.getGroupName()+"%", begin, length);
        } else if(alarmForm.getProjectName() != null) {
            return alarmDao.getAlarmByProjectName(alarmForm.getProjectName()+"%", begin, length);
        } else {
            return alarmDao.getAlarm(begin, length);
        }
    }

    private List<Alarm> getUserAlarm(User user, AlarmForm alarmForm) {
        // 分页参数
        int length = alarmForm.getLength();
        int begin =  (alarmForm.getPage() - 1) * length;

        if(alarmForm.getCode() != null) {
            return alarmDao.getUserAlarmByCode(alarmForm.getCode(), user.getId(), begin, length);
        } else if(alarmForm.getAlarmName() != null) {
            return alarmDao.getUserAlarmByName(alarmForm.getAlarmName()+"%", user.getId(), begin, length);
        } else if(alarmForm.getGroupName() != null){
            return alarmDao.getUserAlarmByGroupName(alarmForm.getGroupName()+"%", user.getId(), begin, length);
        } else if(alarmForm.getProjectName() != null) {
            return alarmDao.getUserAlarmByProjectName(alarmForm.getProjectName()+"%", user.getId(), begin, length);
        } else {
            return alarmDao.getUserAlarm(user.getId(), begin, length);
        }
    }

    /**
     * 获取告警列表的长度，用于分页
     * @param alarmForm
     * @return
     */
    public int getAlarmInfoCount(User user, AlarmForm alarmForm) {
        // 管理员获取所有告警, 普通用户获取自己能接收到的告警
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all alarm info count, user:{}", JSON.toJSONString(user));
            return getAllAlarmCount(alarmForm);
        } else {
            logger.debug("get user alarm info count, user:{}", JSON.toJSONString(user));
            return getUserAlarmCount(user, alarmForm);
        }
    }

    private int getAllAlarmCount(AlarmForm alarmForm) {
        if(alarmForm.getCode() != null) {
            return alarmDao.getAlarmCountByCode(alarmForm.getCode());
        } else if(alarmForm.getAlarmName() != null) {
            return alarmDao.getAlarmCountByName(alarmForm.getAlarmName()+"%");
        } else if(alarmForm.getGroupName() != null){
            return alarmDao.getAlarmCountByGroupName(alarmForm.getGroupName()+"%");
        } else if(alarmForm.getProjectName() != null) {
            return alarmDao.getAlarmCountByProjectName(alarmForm.getProjectName()+"%");
        } else {
            return alarmDao.getAlarmCount();
        }
    }

    private int getUserAlarmCount(User user, AlarmForm alarmForm) {
        if(alarmForm.getCode() != null) {
            return alarmDao.getUserAlarmCountByCode(alarmForm.getCode(), user.getId());
        } else if(alarmForm.getAlarmName() != null) {
            return alarmDao.getUserAlarmCountByName(alarmForm.getAlarmName()+"%", user.getId());
        } else if(alarmForm.getGroupName() != null){
            return alarmDao.getUserAlarmCountByGroupName(alarmForm.getGroupName()+"%", user.getId());
        } else if(alarmForm.getProjectName() != null) {
            return alarmDao.getUserAlarmCountByProjectName(alarmForm.getProjectName()+"%", user.getId());
        } else {
            return alarmDao.getUserAlarmCount(user.getId());
        }
    }

    /**
     * 根据id获取告警
     * @param aid
     * @return
     */
    public Alarm getAlarmById(Integer aid) {
        return alarmDao.getById(aid);
    }

    /**
     * 创建告警
     * @param alarm
     * @return
     */
    public int createAlarm(Alarm alarm) {
        // 自动生成code
        if(alarm.getCode() <= 0) {
            alarm.setCode(alarmDao.getMaxCode() + 1);
        }
        return alarmDao.save(alarm);
    }

    /**
     * 根据id删除告警
     * @param aid
     * @return
     */
    public int deleteAlarmById(Integer aid) {
        return alarmDao.deleteById(aid);
    }

    /**
     * 更新告警信息
     * @param alarm
     * @return
     */
    public int updateAlarm(Alarm alarm) {
        if (alarm.getCode() <= 0) {
            alarm.setCode(alarmDao.getMaxCode() + 1);
        }
        return alarmDao.updateById(alarm);
    }

    /**
     * 获取告警代号列表
     * @param user
     * @return
     */
    public List<Integer> getAlarmCodeList(User user) {
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all alarm code list, user:{}", JSON.toJSONString(user));
            return alarmDao.getAlarmCodeList();
        } else {
            logger.debug("get user alarm code list, user:{}", JSON.toJSONString(user));
            return alarmDao.getUserAlarmCodeList(user.getId());
        }
    }
}
