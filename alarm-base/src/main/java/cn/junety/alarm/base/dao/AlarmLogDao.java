package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.base.entity.DeliveryStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmLogDao {

    @Insert("insert into tb_alarm_log(report_id, code, alarm_name, project_name, module_name, level, receivers," +
            " content, ip, status, delivery_status, create_time) values(#{reportId}, #{code}, #{alarmName}, " +
            " #{projectName}, #{moduleName}, #{level}, #{receivers}, #{content}, #{ip}, #{status}, #{deliveryStatus}, " +
            " #{createTime})")
    void save(AlarmLog alarmLog);

    @Update("update tb_alarm_log set delivery_status=concat(delivery_status, #{channel}, ',') where id=#{logId}")
    int updateDeliveryStatus(DeliveryStatus deliveryStatus);
}
