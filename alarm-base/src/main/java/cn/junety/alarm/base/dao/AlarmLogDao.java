package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.AlarmLog;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmLogDao {

    @Insert("insert into tb_alarm_log(report_id, code, alarm_name, project_name, module_name, level, receivers," +
            " content, ip, status, delivery_status, create_time) values(#{reportId}, #{code}, #{alarmName}, " +
            " #{projectName}, #{moduleName}, #{level}, #{receivers}, #{content}, #{ip}, #{status}, #{deliveryStatus}, " +
            " #{createTime})")
    void save(AlarmLog alarmLog);
}
