package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Alarm;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmDao {
    @Select("select * from tb_alarm where code=#{code} order by id desc")
    List<Alarm> getAllByCode(@Param("code") int code);
}
