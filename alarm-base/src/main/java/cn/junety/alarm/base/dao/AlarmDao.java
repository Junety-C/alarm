package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Alarm;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmDao {

    @Select("select id, code, name, module_id, group_id, route_key, config from tb_alarm where code=#{code}")
    List<Alarm> getByCode(@Param("code") int code);

    @Select("select id, code, name, module_id, group_id, route_key, config from tb_alarm where code=#{code} and route_key=#{route_key}")
    List<Alarm> getByCodeWithRouteKey(@Param("code") int code, @Param("route_key") String routeKey);

    @Select("select id, code, name, module_id, group_id, route_key, config from tb_alarm limit #{begin}, #{length}")
    List<Alarm> get(@Param("begin") int begin, @Param("length") int length);

    @Select("select id, code, name, module_id, group_id, route_key, config from tb_alarm where id=#{id}")
    Alarm getById(@Param("id") int id);

    @Insert("insert into tb_alarm(code, name, module_id, group_id, route_key, config) values(#{code}, #{name}, #{moduleId}, #{groupId}, #{routeKey}, #{config})")
    int save(Alarm alarm);

    @Update("update tb_alarm set code=#{code}, name=#{name}, module_id=#{moduleId}, group_id=#{groupId}, route_key=#{routeKey}, config=#{config} where id=#{id}")
    int updateById(Alarm alarm);

    @Delete("delete from tb_alarm where id=#{id}")
    int deleteById(int id);
}
