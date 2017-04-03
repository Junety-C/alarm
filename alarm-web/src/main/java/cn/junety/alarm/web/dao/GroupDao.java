package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.web.vo.GroupSearch;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 */
public interface GroupDao {

    @Insert("insert into tb_group(name) values(#{name})")
    int save(@Param("name") String name);

    @Delete("delete from tb_group where id=#{id}")
    int deleteById(@Param("id") int id);

    @Delete("delete from tb_group_member where group_id=#{gid}")
    int deleteReceiverByGroupId(@Param("gid") int gid);

    @Insert("insert into tb_group_member(group_id, receiver_id) values(#{gid}, #{rid}) on duplicate key update group_id=#{gid}")
    int saveReceiverToGroup(@Param("gid") int gid, @Param("rid") int rid);

    @Delete("delete from tb_group_member where group_id=#{gid} and receiver_id=#{rid}")
    int removeReceiverFromGroup(@Param("gid") int gid, @Param("rid") int rid);

    @Delete("delete from tb_group_member where receiver_id=#{rid}")
    int deleteReceiver(@Param("rid") int rid);



    @Select("select id, name from tb_group where id=#{id}")
    Group getGroupById(@Param("id") int id);

    @Select("select id, name from tb_group")
    List<Group> getAllGroup();


    /* ===============管理员查询================== */

    @Select("select id, name from tb_group " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Group> getGroup(GroupSearch groupSearch);

    @Select("select count(id) from tb_group")
    int getGroupCount();

    @Select("select id, name from tb_group where name like '${groupName}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Group> getGroupByName(GroupSearch groupSearch);

    @Select("select count(id) from tb_group where name like '${groupName}%'")
    int getGroupCountByName(GroupSearch groupSearch);


    /* ===============用户查询================== */

    @Select("select tg.id, tg.name from tb_group tg, tb_group_member tgm " +
            "where tg.id=tgm.group_id and tgm.receiver_id in" +
            "(select receiver_id from tb_user tu, tb_user_to_receiver tur where tu.id=tur.user_id and tu.id=#{userId}) " +
            "order by tg.id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Group> getUserGroup(GroupSearch groupSearch);

    @Select("select count(tg.id) from tb_group tg, tb_group_member tgm " +
            "where tg.id=tgm.group_id and tgm.receiver_id in" +
            "(select receiver_id from tb_user tu, tb_user_to_receiver tur where tu.id=tur.user_id and tu.id=#{userId})")
    int getUserGroupCount();

    @Select("select tg.id, tg.name from tb_group tg, tb_group_member tgm " +
            "where tg.name like '${groupName}%' and tg.id=tgm.group_id and tgm.receiver_id in" +
            "(select receiver_id from tb_user tu, tb_user_to_receiver tur where tu.id=tur.user_id and tu.id=#{userId}) " +
            "order by tg.id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Group> getUserGroupByName(GroupSearch groupSearch);

    @Select("select count(tg.id) from tb_group tg, tb_group_member tgm " +
            "where tg.name like '${groupName}%' and tg.id=tgm.group_id and tgm.receiver_id in" +
            "(select receiver_id from tb_user tu, tb_user_to_receiver tur where tu.id=tur.user_id and tu.id=#{userId})")
    int getUserGroupCountByName(GroupSearch groupSearch);
}
