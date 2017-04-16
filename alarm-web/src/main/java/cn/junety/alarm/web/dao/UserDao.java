package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.vo.UserSearch;
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

    @Select("select * from tb_user where id=#{id}")
    User getUserById(@Param("id") int id);

    @Insert("insert into tb_user(account, name, identification, type, mail, phone, wechat, qq) " +
            "values(#{account}, #{name}, #{identification}, #{type}, #{mail}, #{phone}, #{wechat}, #{qq})")
    int save(User user);

    @Update("update tb_user set name=#{name}, type=#{type}, phone=#{phone}, mail=#{mail}, wechat=#{wechat}, qq=#{qq} " +
            "where id=#{id}")
    int update(User user);

    @Delete("delete from tb_user where id=#{id}")
    int delete(int id);

    @Select("select * from tb_user order by id desc limit #{page.start}, #{page.pageSize}")
    List<User> getUser(UserSearch userSearch);

    @Select("select count(id) from tb_user")
    int getUserCount();

    @Select("select * from tb_user " +
            "where name like '${name}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<User> getUserByName(UserSearch userSearch);

    @Select("select count(id) from tb_user where name like '${name}%'")
    int getUserCountByName(UserSearch userSearch);

    @Select("select * from tb_user " +
            "where account like '${account}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<User> getUserByAccount(UserSearch userSearch);

    @Select("select count(id) from tb_user where account like '${account}%'")
    int getUserCountByAccount(UserSearch userSearch);
}
