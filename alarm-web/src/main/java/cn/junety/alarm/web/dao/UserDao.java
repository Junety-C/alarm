package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/4/2.
 */
public interface UserDao {

    @Select("select id, username, identification, type from tb_user where username=#{username}")
    User getUserByUsername(@Param("username") String username);

    @Select("select id, username, identification, type from tb_user where identification=#{identification}")
    User getUserByIdentification(@Param("identification") String identification);

    @Select("select tu.id, tu.username, tr.name from tb_user tu, tb_receiver tr, tb_user_to_receiver tur " +
            "where tu.id=tur.user_id and tr.id=tur.receiver_id")
    List<UserVO> getAllUser();
}
