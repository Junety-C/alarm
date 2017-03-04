package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Alarm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmDao {

    @Select("select id, code, name, module_id, group_id, route_key, config from tb_alarm where code=#{code}")
    List<Alarm> get(@Param("code") int code);

    @Select("select id, code, name, module_id, group_id, route_key, config from tb_alarm where code=#{code} and route_key=#{route_key}")
    List<Alarm> getWithRouteKey(@Param("code") int code, @Param("route_key") String routeKey);
}
