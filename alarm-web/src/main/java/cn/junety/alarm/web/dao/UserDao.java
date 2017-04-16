package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/4/2.
 */
public interface UserDao {

    @Select("select * from tb_user where account=#{account}")
    User getUserByAccount(@Param("account") String account);

    @Select("select * from tb_user where identification=#{identification}")
    User getUserByIdentification(@Param("identification") String identification);

    @Select("select * from tb_user")
    List<UserVO> getAllUser();
}
