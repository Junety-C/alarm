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

    @Delete("delete from tb_project_member where project_id=#{id}")
    int deleteProjectMemberById(@Param("id") int id);

    @Select("select tu.id, tu.username, tr.name " +
            "from tb_user tu, tb_receiver tr, tb_project_member tpu, tb_user_to_receiver tur " +
            "where tpu.project_id=#{id} and tpu.user_id=tu.id and tur.user_id=tu.id and tur.receiver_id=tr.id")
    List<UserVO> getProjectMemberBytId(@Param("id") int id);

    @Insert("insert into tb_project_member(project_id, user_id) values(#{pid}, #{uid}) " +
            "on duplicate key update project_id=#{pid}, user_id=#{uid}")
    int addUserToProject(@Param("uid") int uid, @Param("pid") int pid);

    @Delete("delete from tb_project_member where project_id=#{pid} and user_id=#{uid}")
    int removeUserFromProject(@Param("uid") int uid, @Param("pid") int pid);




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
