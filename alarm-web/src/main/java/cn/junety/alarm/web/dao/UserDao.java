package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/4/2.
 */
public interface UserDao {

    @Select("select id, username, name, identification, type from tb_user where username=#{username}")
    User getByUsername(@Param("username") String username);

    @Select("select id, username, name, identification, type from tb_user where identification=#{identification}")
    User getByIdentification(@Param("identification") String identification);
}
