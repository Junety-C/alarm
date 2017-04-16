package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.web.vo.ProjectSearch;
import cn.junety.alarm.web.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ProjectDao {

    @Select("select id, name from tb_project order by id desc")
    List<Project> getAllProject();

    @Select("select * from tb_project where id=#{id}")
    Project getProjectById(@Param("id") int id);

    @Insert("insert into tb_project(name, creater, create_time, comment) " +
            "values(#{name}, #{creater}, #{createTime}, #{comment})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Project project);

    @Update("update tb_project set name=#{name}, comment=#{comment} where id=#{id}")
    void update(Project project);

    @Delete("delete from tb_project where id=#{id}")
    int delete(@Param("id") int id);

    @Select("select type from tb_project_member where user_id=#{userId} and project_id=#{projectId}")
    int getProjectMemberTypeByProjectId(@Param("userId") int userId, @Param("projectId") int projectId);

    /* ===============管理员查询================== */

    @Select("select * from tb_project order by id desc limit #{page.start}, #{page.pageSize}")
    List<Project> getProject(ProjectSearch projectSearch);

    @Select("select count(id) from tb_project")
    int getProjectCount();

    /* ===============用户查询================== */

    @Select("select * from tb_project " +
            "where id in (select project_id from tb_project_member where user_id=#{userId}) " +
            "order by id desc limit #{page.start}, #{page.pageSize}")
    List<Project> getUserProject(ProjectSearch projectSearch);

    @Select("select count(id) from tb_project " +
            "where id in (select project_id from tb_project_member where user_id=#{userId})")
    int getUserProjectCount();
}
