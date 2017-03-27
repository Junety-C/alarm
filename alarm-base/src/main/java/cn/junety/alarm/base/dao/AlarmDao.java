package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Alarm;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmDao {

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm " +
            "where code=#{code} " +
            "order by id desc")
    List<Alarm> getAllByCode(@Param("code") int code);

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm " +
            "where code=#{code} and route_key=#{route_key} " +
            "order by id desc")
    List<Alarm> getAllByCodeWithRouteKey(@Param("code") int code, @Param("route_key") String routeKey);


    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm " +
            "where id=#{id} " +
            "order by id desc")
    Alarm getAllById(@Param("id") int id);

    @Insert("insert into tb_alarm(code, name, project_id, module_id, group_id, route_key, config) " +
            "values(#{code}, #{name}, #{projectId}, #{moduleId}, #{groupId}, #{routeKey}, #{config})")
    int save(Alarm alarm);

    @Update("update tb_alarm " +
            "set code=#{code}, name=#{name}, project_id=#{projectId}, module_id=#{moduleId}, " +
            "group_id=#{groupId}, route_key=#{routeKey}, config=#{config} " +
            "where id=#{id}")
    int updateById(Alarm alarm);

    @Delete("delete from tb_alarm where id=#{id}")
    int deleteById(int id);


    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm " +
            "where code=#{code} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Alarm> getByCode(@Param("code") int code, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm where code=#{code}")
    int getCountByCode(@Param("code") int code);

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm " +
            "where name like #{name} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Alarm> getByName(@Param("name") String name, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm where name like #{name}")
    int getCountByName(@Param("name") String name);

    @Select("select ta.id, code, ta.name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm ta, tb_group tb " +
            "where tb.name like #{groupName} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Alarm> getByGroupName(@Param("groupName") String groupName, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(ta.id) from tb_alarm ta, tb_group tb where tb.name like #{groupName}")
    int getCountByGroupName(@Param("groupName") String groupName);

    @Select("select ta.id, code, ta.name, ta.project_id, module_id, group_id, route_key, config " +
            "from tb_alarm ta, tb_project tp " +
            "where ta.project_id=tp.id and tp.name like #{projectName} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Alarm> getByProjectName(@Param("projectName") String projectName, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(ta.id) from tb_alarm ta, tb_project tp " +
            "where ta.project_id=tp.id and tp.name like #{projectName} ")
    int getCountByProjectName(@Param("projectName") String projectName);

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Alarm> get(@Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_alarm")
    int getCount();

    @Select("select distinct code from tb_alarm")
    List<Integer> getCodes();

    @Select("select max(code) from tb_alarm")
    int getMaxCode();

    @Select("select id, code, name, project_id, module_id, group_id, route_key, config " +
            "from tb_alarm where id=#{id}")
    Alarm getById(@Param("id") int id);
}
