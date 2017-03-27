package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.AlarmLogDao;
import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.web.vo.AlarmLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 */
@Service
public class AlarmLogService {

    @Autowired
    private AlarmLogDao alarmLogDao;

    public List<AlarmLog> getLogs(AlarmLogForm alarmLogForm) {
        int length = alarmLogForm.getLength();
        int begin =  (alarmLogForm.getPage() - 1) * length;

        if(alarmLogForm.getCode() != null) {
            return alarmLogDao.getByCode(alarmLogForm.getCode(), begin, length);
        } else if(alarmLogForm.getAlarmName() != null) {
            return alarmLogDao.getByAlarmName(alarmLogForm.getAlarmName()+"%", begin, length);
        } else if(alarmLogForm.getProjectName() != null) {
            return alarmLogDao.getByProjectName(alarmLogForm.getProjectName()+"%", begin, length);
        } else {
            return alarmLogDao.get(begin, length);
        }
    }

    public int getLogCount(AlarmLogForm alarmLogForm) {
        if(alarmLogForm.getCode() != null) {
            return alarmLogDao.getCountByCode(alarmLogForm.getCode());
        } else if(alarmLogForm.getAlarmName() != null) {
            return alarmLogDao.getCountByAlarmName(alarmLogForm.getAlarmName()+"%");
        } else if(alarmLogForm.getProjectName() != null) {
            return alarmLogDao.getCountByProjectName(alarmLogForm.getProjectName()+"%");
        }
        return alarmLogDao.getCount();
    }
}
