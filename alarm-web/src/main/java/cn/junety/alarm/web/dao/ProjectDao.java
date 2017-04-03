package cn.junety.alarm.web.dao;

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

    @Select("select id, name from tb_project order by id desc")
    List<Project> getAllProject();

    @Select("select id, name from tb_project order by id desc limit #{begin}, #{length}")
    List<Project> getProject(@Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_project")
    int getProjectCount();

    @Select("select id, name from tb_project where name like #{name} order by id desc limit #{begin}, #{length}")
    List<Project> getProjectByName(@Param("name") String name, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_project where name like #{name}")
    int getProjectCountByName(@Param("name") String name);

    @Insert("insert into tb_project(name) values(#{name})")
    int save(@Param("name") String name);

    @Delete("delete from tb_project where id=#{id}")
    int deleteById(@Param("id") int id);
}
