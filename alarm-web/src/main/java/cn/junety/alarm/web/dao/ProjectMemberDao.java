package cn.junety.alarm.web.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * Created by caijt on 2017/4/16.
 */
public interface ProjectMemberDao {

    @Insert("insert into tb_project_member(user_id, project_id, type) " +
            "values(#{userId}, #{projectId}, #{type})")
    void addProjectMember(@Param("userId") int userId, @Param("projectId") int projectId, @Param("type") int type);

    @Delete("delete from tb_project_member where project_id=#{projectId}")
    void deleteProjectMemberByProjectId(@Param("projectId") int projectId);
}
