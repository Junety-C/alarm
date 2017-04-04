package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.AlarmLog;
import cn.junety.alarm.web.vo.AlarmLogSearch;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmLogDao {

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time from tb_alarm_log " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<AlarmLog> getLog(AlarmLogSearch alarmLogSearch);

    @Select("select count(id) from tb_alarm_log")
    int getLogCount();

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time " +
            "from tb_alarm_log " +
            "where code=#{code} order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<AlarmLog> getLogByCode(AlarmLogSearch alarmLogSearch);

    @Select("select count(id) from tb_alarm_log where code=#{code}")
    int getLogCountByCode(AlarmLogSearch alarmLogSearch);

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time " +
            "from tb_alarm_log " +
            "where alarm_name like '${alarmName}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<AlarmLog> getLogByAlarmName(AlarmLogSearch alarmLogSearch);

    @Select("select count(id) from tb_alarm_log where alarm_name like '${alarmName}%'")
    int getLogCountByAlarmName(AlarmLogSearch alarmLogSearch);

    @Select("select id, report_id, code, alarm_name, project_name, module_name, group_name, level, receivers, " +
            "content, ip, status, delivery_status, create_time " +
            "from tb_alarm_log where project_name like '${projectName}%' order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<AlarmLog> getLogByProjectName(AlarmLogSearch alarmLogSearch);

    @Select("select count(id) from tb_alarm_log where project_name like '${projectName}%'")
    int getLogCountByProjectName(AlarmLogSearch alarmLogSearch);

}
