package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.web.dao.AlarmLogDao;
import cn.junety.alarm.web.vo.AlarmLogSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 */
@Service
public class AlarmLogService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmLogService.class);

    @Autowired
    private AlarmLogDao alarmLogDao;

    public List<AlarmLog> getLogList(AlarmLogSearch alarmLogSearch) {
        if(alarmLogSearch.getCode() != null) {
            return alarmLogDao.getLogByCode(alarmLogSearch);
        } else if(alarmLogSearch.getAlarmName() != null) {
            return alarmLogDao.getLogByAlarmName(alarmLogSearch);
        } else if(alarmLogSearch.getProjectName() != null) {
            return alarmLogDao.getLogByProjectName(alarmLogSearch);
        } else {
            return alarmLogDao.getLog(alarmLogSearch);
        }
    }

    public int getLogCount(AlarmLogSearch alarmLogSearch) {
        if(alarmLogSearch.getCode() != null) {
            return alarmLogDao.getLogCountByCode(alarmLogSearch);
        } else if(alarmLogSearch.getAlarmName() != null) {
            return alarmLogDao.getLogCountByAlarmName(alarmLogSearch);
        } else if(alarmLogSearch.getProjectName() != null) {
            return alarmLogDao.getLogCountByProjectName(alarmLogSearch);
        }
        return alarmLogDao.getLogCount();
    }
}
