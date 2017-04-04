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

    @Select("select id, name from tb_project where id=#{id}")
    Project getProjectById(@Param("id") int id);

    @Select("select id, name from tb_project order by id desc")
    List<Project> getAllProject();

    @Insert("insert into tb_project(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Project project);

    @Delete("delete from tb_project where id=#{id}")
    int deleteById(@Param("id") int id);

    @Select("select tu.id, tu.username, tr.name " +
            "from tb_user tu, tb_receiver tr, tb_project_to_user tpu, tb_user_to_receiver tur " +
            "where tpu.project_id=#{id} and tpu.user_id=tu.id and tur.user_id=tu.id and tur.receiver_id=tr.id")
    List<UserVO> getProjectMemberBytId(@Param("id") int id);

    @Insert("insert into tb_project_to_user(project_id, user_id) values(#{pid}, #{uid}) " +
            "on duplicate key update project_id=#{pid}, user_id=#{uid}")
    int addUserToProject(@Param("uid") int uid, @Param("pid") int pid);

    @Delete("delete from tb_project_to_user where project_id=#{pid} and user_id=#{uid}")
    int removeUserFromProject(@Param("uid") int uid, @Param("pid") int pid);

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
