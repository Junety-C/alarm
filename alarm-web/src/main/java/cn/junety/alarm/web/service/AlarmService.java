package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.AlarmDao;
import cn.junety.alarm.base.dao.GroupDao;
import cn.junety.alarm.base.dao.ModuleDao;
import cn.junety.alarm.base.dao.ProjectDao;
import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.vo.AlarmForm;
import cn.junety.alarm.web.vo.AlarmVO;
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

    public List<AlarmVO> getAlarmInfo(AlarmForm alarmForm) {
        int length = alarmForm.getLength();
        int begin =  (alarmForm.getPage() - 1) * length;
        List<Alarm> alarms;

        if(alarmForm.getCode() != null) {
            alarms = alarmDao.getByCode(alarmForm.getCode(), begin, length);
        } else if(alarmForm.getAlarmName() != null) {
            alarms = alarmDao.getByName(alarmForm.getAlarmName()+"%", begin, length);
        } else if(alarmForm.getGroupName() != null){
            alarms = alarmDao.getByGroupName(alarmForm.getGroupName()+"%", begin, length);
        } else if(alarmForm.getProjectName() != null) {
            alarms = alarmDao.getByProjectName(alarmForm.getProjectName()+"%", begin, length);
        } else {
            alarms = alarmDao.get(begin, length);
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

    public int getAlarmInfoCount(AlarmForm alarmForm) {
        if(alarmForm.getCode() != null) {
            return alarmDao.getCountByCode(alarmForm.getCode());
        } else if(alarmForm.getAlarmName() != null) {
            return alarmDao.getCountByName(alarmForm.getAlarmName()+"%");
        } else if(alarmForm.getGroupName() != null){
            return alarmDao.getCountByGroupName(alarmForm.getGroupName()+"%");
        } else if(alarmForm.getProjectName() != null) {
            return alarmDao.getCountByProjectName(alarmForm.getProjectName()+"%");
        }
        return alarmDao.getCount();
    }

    public Alarm getAlarmById(Integer aid) {
        return alarmDao.getById(aid);
    }

    public int createAlarm(Alarm alarm) {
        // 自动生成code
        if(alarm.getCode() <= 0) {
            alarm.setCode(alarmDao.getMaxCode() + 1);
        }
        return alarmDao.save(alarm);
    }

    public List<Integer> getCodes() {
        return alarmDao.getCodes();
    }

    public List<Project> getProjects() {
        return projectDao.getAll();
    }

    public List<Group> getGroups() {
        return groupDao.getAll();
    }

    public List<Module> getModules(int pid) {
        return moduleDao.getByPid(pid);
    }

    public int deleteAlarmById(Integer aid) {
        return alarmDao.deleteById(aid);
    }

    public int updateAlarm(Alarm alarm) {
        if (alarm.getCode() <= 0) {
            alarm.setCode(alarmDao.getMaxCode() + 1);
        }
        return alarmDao.updateById(alarm);
    }
}
