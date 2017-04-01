package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 */
public interface GroupDao {
    @Select("select id, name from tb_group where id=#{id}")
    Group getById(@Param("id") int id);

    @Select("select id, name from tb_group")
    List<Group> getAll();

    @Select("select id, name from tb_group order by id desc limit #{begin}, #{length}")
    List<Group> get(@Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_group")
    int getCount();

    @Select("select id, name from tb_group where name like #{name} order by id desc limit #{begin}, #{length}")
    List<Group> getByName(@Param("name") String name, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_group where name like #{name}")
    int getCountByName(@Param("name") String name);

    @Insert("insert into tb_group(name) values(#{name})")
    int save(Group group);

    @Update("update tb_group set name=#{name} where id=#{id}")
    int updateById(@Param("id") int id);

    @Delete("delete from tb_group where id=#{id}")
    int deleteById(@Param("id") int id);

    @Delete("delete from tb_group_member where group_id=#{gid}")
    int deleteReceiverByGroupId(@Param("gid") int gid);

    @Insert("insert into tb_group_member(group_id, receiver_id) values(#{gid}, #{rid}) on duplicate key update group_id=#{gid}")
    int saveReceiverToGroup(@Param("gid") int gid, @Param("rid") int rid);

    @Delete("delete from tb_group_member where group_id=#{gid} and receiver_id=#{rid}")
    int deleteReceiverFromGroup(@Param("gid") int gid, @Param("rid") int rid);

    @Delete("delete from tb_group_member where receiver_id=#{rid}")
    int deleteReceiver(@Param("rid") int rid);
}
