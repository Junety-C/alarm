package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.web.vo.ProjectSearch;
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

    @Insert("insert into tb_project(name) values(#{name})")
    int save(@Param("name") String name);

    @Delete("delete from tb_project where id=#{id}")
    int deleteById(@Param("id") int id);

    /* ===============管理员查询================== */

    @Select("select id, name from tb_project order by id desc limit #{page.start}, #{page.pageSize}")
    List<Project> getProject(ProjectSearch projectSearch);

    @Select("select count(id) from tb_project")
    int getProjectCount();

    @Select("select id, name from tb_project where name like '${projectName}%' " +
            "order by id desc limit #{page.start}, #{page.pageSize}")
    List<Project> getProjectByName(ProjectSearch projectSearch);

    @Select("select count(id) from tb_project where name like '${projectName}%'")
    int getProjectCountByName(ProjectSearch projectSearch);

    /* ===============用户查询================== */

    @Select("select id, name from tb_project " +
            "where id in (select project_id from tb_project_to_user where user_id=#{userId}) " +
            "order by id desc limit #{page.start}, #{page.pageSize}")
    List<Project> getUserProject(ProjectSearch projectSearch);

    @Select("select count(id) from tb_project " +
            "where id in (select project_id from tb_project_to_user where user_id=#{userId}) ")
    int getUserProjectCount();

    @Select("select id, name from tb_project where name like '${projectName}%' " +
            "and id in (select project_id from tb_project_to_user where user_id=#{userId}) " +
            "order by id desc limit #{page.start}, #{page.pageSize}")
    List<Project> getUserProjectByName(ProjectSearch projectSearch);

    @Select("select count(id) from tb_project where name like '${projectName}%' " +
            "and id in (select project_id from tb_project_to_user where user_id=#{userId}) ")
    int getUserProjectCountByName(ProjectSearch projectSearch);
}
