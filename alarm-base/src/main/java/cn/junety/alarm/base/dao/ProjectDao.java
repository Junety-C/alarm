package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ProjectDao {
    @Select("select id, name from tb_project where id=#{id}")
    Project get(@Param("id") int id);
}
