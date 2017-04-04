package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.AlarmLog;
import org.apache.ibatis.annotations.*;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmLogDao {
    @Insert("insert into tb_alarm_log(report_id, code, alarm_name, project_name, module_name, group_name, level, " +
            "receivers, content, ip, status, delivery_status, create_time) values(#{reportId}, #{code}, #{alarmName}, " +
            "#{projectName}, #{moduleName}, #{groupName}, #{level}, #{receivers}, #{content}, #{ip}, " +
            "#{status}, #{deliveryStatus}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(AlarmLog alarmLog);
}
