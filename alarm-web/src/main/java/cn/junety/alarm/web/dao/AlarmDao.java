package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Alarm;
import cn.junety.alarm.web.vo.AlarmSearch;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 * 告警dao
 */
public interface AlarmDao {

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm where id=#{id}")
    Alarm getAlarmById(@Param("id") int id);

    @Insert("insert into tb_alarm(code, name, project_id, module_id, group_id, route_key, config) " +
            "values(#{code}, #{name}, #{projectId}, #{moduleId}, #{groupId}, #{routeKey}, #{config})")
    int save(Alarm alarm);

    @Update("update tb_alarm " +
            "set code=#{code}, name=#{name}, project_id=#{projectId}, module_id=#{moduleId}, " +
            "group_id=#{groupId}, route_key=#{routeKey}, config=#{config} " +
            "where id=#{id}")
    int updateById(Alarm alarm);

    @Delete("delete from tb_alarm where id=#{id}")
    int deleteById(@Param("id") int id);

    /* ===============管理员查询================== */

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getAlarm(AlarmSearch alarmSearch);

    @Select("select count(id) from tb_alarm")
    int getAlarmCount();

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm " +
            "where code=#{code} " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getAlarmByCode(AlarmSearch alarmSearch);

    @Select("select count(id) from tb_alarm where code=#{code}")
    int getAlarmCountByCode(AlarmSearch alarmSearch);

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm " +
            "where name like '${alarmName}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getAlarmByName(AlarmSearch alarmSearch);

    @Select("select count(id) from tb_alarm where name like '${alarmName}%'")
    int getAlarmCountByName(AlarmSearch alarmSearch);

    @Select("select ta.id, code, ta.name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm ta, tb_group tb " +
            "where tb.name like '${groupName}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getAlarmByGroupName(AlarmSearch alarmSearch);

    @Select("select count(ta.id) from tb_alarm ta, tb_group tb where tb.name like '${groupName}%'")
    int getAlarmCountByGroupName(AlarmSearch alarmSearch);

    @Select("select ta.id, code, ta.name, ta.project_id, module_id, group_id, route_key, config " +
            "from tb_alarm ta, tb_project tp " +
            "where ta.project_id=tp.id and tp.name like '${projectName}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getAlarmByProjectName(AlarmSearch alarmSearch);

    @Select("select count(ta.id) from tb_alarm ta, tb_project tp " +
            "where ta.project_id=tp.id and tp.name like '${projectName}%'")
    int getAlarmCountByProjectName(AlarmSearch alarmSearch);


    /* ===============用户查询================== */

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm " +
            "where project_id in (select project_id from tb_project_member where user_id=#{userId}) " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getUserAlarm(AlarmSearch alarmSearch);

    @Select("select count(id) from tb_alarm " +
            "where project_id in (select project_id from tb_project_member where user_id=#{userId})")
    int getUserAlarmCount(AlarmSearch alarmSearch);

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm " +
            "where code=#{code} and project_id in (select project_id from tb_project_member where user_id=#{userId}) " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getUserAlarmByCode(AlarmSearch alarmSearch);

    @Select("select count(id) from tb_alarm " +
            "where code=#{code} and project_id in (select project_id from tb_project_member where user_id=#{userId})")
    int getUserAlarmCountByCode(AlarmSearch alarmSearch);

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config from tb_alarm " +
            "where name like '${name}%' and project_id in (select project_id from tb_project_member where user_id=#{userId}) " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getUserAlarmByName(AlarmSearch alarmSearch);

    @Select("select count(id) from tb_alarm " +
            "where name like '${name}%' and project_id in (select project_id from tb_project_member where user_id=#{userId})")
    int getUserAlarmCountByName(AlarmSearch alarmSearch);

    @Select("select ta.id, code, ta.name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm ta, tb_group tb where ta.group_id=tb.id and tb.name like '${groupName}%' " +
            "and project_id in (select project_id from tb_project_member where user_id=#{userId}) " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getUserAlarmByGroupName(AlarmSearch alarmSearch);

    @Select("select count(ta.id) from tb_alarm ta, tb_group tb where ta.group_id=tb.id and tb.name like '${groupName}%' " +
            "and project_id in (select project_id from tb_project_member where user_id=#{userId})")
    int getUserAlarmCountByGroupName(AlarmSearch alarmSearch);

    @Select("select ta.id, code, ta.name, ta.project_id, module_id, group_id, route_key, config " +
            "from tb_alarm ta, tb_project tp " +
            "where ta.project_id=tp.id and tp.name like '${projectName}%' " +
            "and project_id in (select project_id from tb_project_member where user_id=#{userId}) " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Alarm> getUserAlarmByProjectName(AlarmSearch alarmSearch);

    @Select("select count(ta.id) from tb_alarm ta, tb_project tp " +
            "where ta.project_id=tp.id and tp.name like '${projectName}%' " +
            "and project_id in (select project_id from tb_project_member where user_id=#{userId})")
    int getUserAlarmCountByProjectName(AlarmSearch alarmSearch);
}
