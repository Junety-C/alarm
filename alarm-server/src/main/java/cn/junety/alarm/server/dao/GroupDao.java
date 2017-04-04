package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Group;
import org.apache.ibatis.annotations.*;

/**
 * Created by caijt on 2017/3/26.
 */
public interface GroupDao {
    @Select("select id, name from tb_group where id=#{id}")
    Group getGroupById(@Param("id") int id);
}
