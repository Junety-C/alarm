package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by caijt on 2017/4/2.
 */
public interface UserDao {

    @Select("select id, username, name, identification, type from tb_user where username=#{username}")
    User getByUsername(@Param("username") String username);

    @Select("select id, username, name, identification, type from tb_user where identification=#{identification}")
    User getByIdentification(@Param("identification") String identification);

    @Select("select tu.id, tu.username, tu.name, tu.mail, tu.phone, tu.wechat, tu.qq, tu.identification, tu.type" +
            "from tb_user tu, tb_group_member tgm " +
            "where tgm.group_id=#{groupId} and tgm.receiver_id=tu.id")
    List<User> getByGroupId(int groupId);
}
