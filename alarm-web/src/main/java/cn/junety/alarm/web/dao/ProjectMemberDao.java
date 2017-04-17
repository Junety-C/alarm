package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by caijt on 2017/4/16.
 * 项目成员dao
 */
public interface ProjectMemberDao {

    @Insert("insert into tb_project_member(user_id, project_id, type) " +
            "values(#{userId}, #{projectId}, #{type}) " +
            "on duplicate key update type=#{type}")
    void addProjectMember(@Param("projectId") int projectId, @Param("userId") int userId, @Param("type") int type);

    @Delete("delete from tb_project_member where project_id=#{projectId} and user_id=#{userId}")
    void removeProjectMember(@Param("projectId") int projectId, @Param("userId") int userId);

    @Delete("delete from tb_project_member where project_id=#{projectId}")
    void deleteProjectMemberByProjectId(@Param("projectId") int projectId);

    @Select("select id, account, name, tu.type, mail, phone, wechat, qq " +
            "from tb_user tu, tb_project_member tpm " +
            "where project_id=#{projectId} and user_id=id " +
            "order by id desc")
    List<User> getMemberByProjectId(@Param("projectId") int projectId);

    @Select("select tu.id, account, name, tu.type, mail, phone, wechat, qq from tb_user tu, tb_project_member tpm " +
            "where tu.id=tpm.user_id and project_id=#{projectId} and account=#{account}")
    User getProjectMemberByAccount(@Param("projectId") int projectId, @Param("account") String account);

    @Select("select type from tb_project_member where user_id=#{userId} and project_id=#{projectId}")
    int getProjectMemberTypeByProjectId(@Param("userId") int userId, @Param("projectId") int projectId);
}
