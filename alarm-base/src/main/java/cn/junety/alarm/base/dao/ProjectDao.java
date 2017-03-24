package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ProjectDao {
    @Select("select id, name from tb_project where id=#{id}")
    Project getById(@Param("id") int id);

    @Select("select id, name from tb_project limit #{begin}, #{length}")
    List<Project> get(@Param("begin") int begin, @Param("length") int length);

    @Insert("insert into tb_project(name) values(#{name})")
    int save(Project project);

    @Update("update tb_project set name=#{name} where id=#{id}")
    int updateById(@Param("id") int id);

    @Delete("delete from tb_project where id=#{id}")
    int deleteById(@Param("id") int id);

    @Select("select count(id) from tb_project")
    int count();
}
