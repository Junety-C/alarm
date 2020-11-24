package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 * 接收组dao
 */
public interface GroupDao {

    @Select("select id, name from tb_group where id=#{id}")
    Group getGroupById(@Param("id") int id);

    @Insert("insert into tb_group(project_id, name) values(#{projectId}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Group group);

    @Select("select * from tb_group where project_id=#{projectId} order by id desc")
    List<Group> getGroupByProjectId(@Param("projectId") int projectId);

    @Select("select id, account, name, mail, phone, wechat, qq from tb_user " +
            "where id in (select user_id from tb_group_member where group_id=#{groupId}) " +
            "order by id desc")
    List<User> getMemberListByGroupId(@Param("groupId") int groupId);

    @Insert("insert into tb_group_member(group_id, user_id) values(#{groupId}, #{userId})")
    void addGroupMember(@Param("groupId") int groupId, @Param("userId") int userId);

    @Delete("delete from tb_group where id=#{groupId}")
    int deleteGroupById(@Param("groupId") int groupId);

    @Delete("delete from tb_group_member where group_id=#{groupId}")
    void removeGroupMemberByGroupId(@Param("groupId") int groupId);

    @Delete("delete from tb_group_member where group_id=#{groupId} and user_id=#{userId}")
    void removeGroupMember(@Param("groupId") int groupId, @Param("userId") int userId);
}
