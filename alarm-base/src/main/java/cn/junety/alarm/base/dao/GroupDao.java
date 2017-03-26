package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 */
public interface GroupDao {
    @Select("select id, name from tb_group where id=#{id}")
    Group getById(@Param("id") int id);

    @Select("select id, name from tb_group")
    List<Group> getAll();
}
