package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.vo.UserVO;
import org.apache.ibatis.annotations.*;

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

    @Select("select id, name, tpm.type " +
            "from tb_user tu, tb_project_member tpm " +
            "where tpm.project_id=#{projectId} and tpm.user_id=tu.id " +
            "order by id desc")
    List<UserVO> getMemberByProjectId(@Param("projectId") int projectId);

    @Select("select tu.id, account, name, tu.type, mail, phone, wechat, qq from tb_user tu, tb_project_member tpm " +
            "where tu.id=tpm.user_id and project_id=#{projectId} and account=#{account}")
    User getProjectMemberByAccount(@Param("projectId") int projectId, @Param("account") String account);

    @Select("select type from tb_project_member where user_id=#{userId} and project_id=#{projectId}")
    Integer getProjectMemberTypeByProjectId(@Param("userId") int userId, @Param("projectId") int projectId);

    @Update("update tb_project_member set type=#{type} where project_id=#{projectId} and user_id=#{userId}")
    void updateType(@Param("projectId") int projectId, @Param("userId") int userId, @Param("type") int type);
}
