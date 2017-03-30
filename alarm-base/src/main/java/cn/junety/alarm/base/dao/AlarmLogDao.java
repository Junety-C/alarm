package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.base.entity.DeliveryStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmLogDao {

    @Insert("insert into tb_alarm_log(report_id, code, alarm_name, project_name, module_name, group_name, level, " +
            "receivers, content, ip, status, delivery_status, create_time) values(#{reportId}, #{code}, #{alarmName}, " +
            "#{projectName}, #{moduleName}, #{groupName}, #{level}, #{receivers}, #{content}, #{ip}, #{status}, #{deliveryStatus}, " +
            "#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(AlarmLog alarmLog);

    @Update("update tb_alarm_log set status=#{status}, delivery_status=concat(delivery_status, #{channel}, ',') where id=#{logId}")
    int updateDeliveryStatus(@Param("status") int status, DeliveryStatus deliveryStatus);

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time from tb_alarm_log " +
            "order by id desc limit #{begin}, #{length}")
    List<AlarmLog> get(@Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm_log")
    int getCount();

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time " +
            "from tb_alarm_log where code=#{code} order by id desc " +
            "limit #{begin}, #{length}")
    List<AlarmLog> getByCode(@Param("code") int code, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm_log where code=#{code}")
    int getCountByCode(@Param("code") int code);

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time " +
            "from tb_alarm_log where alarm_name like #{alarmName} order by id desc " +
            "limit #{begin}, #{length}")
    List<AlarmLog> getByAlarmName(@Param("alarmName") String alarmName, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm_log where alarm_name like #{alarmName}")
    int getCountByAlarmName(@Param("alarmName") String alarmName);

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time " +
            "from tb_alarm_log where project_name like #{projectName} order by id desc " +
            "limit #{begin}, #{length}")
    List<AlarmLog> getByProjectName(@Param("projectName") String projectName, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm_log where project_name like #{projectName}")
    int getCountByProjectName(@Param("projectName") String projectName);

}
