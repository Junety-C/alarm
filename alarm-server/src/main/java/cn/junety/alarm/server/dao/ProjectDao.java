package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ProjectDao {
    @Select("select id, name from tb_project where id=#{id}")
    Project getProjectById(@Param("id") int id);
}
