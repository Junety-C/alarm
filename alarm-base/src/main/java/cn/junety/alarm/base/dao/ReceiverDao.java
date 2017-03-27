package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Receiver;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by caijt on 2017/1/28.
 */
public interface ReceiverDao {
    @Select("select re.* from tb_receiver re left join tb_group_member gm on re.id=gm.receiver_id and gm.group_id=#{group_id} ")
    List<Receiver> getByGroupId(int groupId);

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver order by id desc limit #{begin}, #{length}")
    List<Receiver> get(@Param("begin") int begin, @Param("length") int length);

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver where id=#{id}")
    Receiver getById(@Param("id") int id);

    @Insert("insert into tb_receiver(name, phone, mail, wechat, qq) values(#{name}, #{phone}, #{mail}, #{wechat}, #{qq})")
    int save(Receiver receiver);

    @Update("update tb_receiver set name=#{name}, phone=#{phone}, mail=#{mail}, wechat=#{wechat}, qq=#{qq} where id=#{id}")
    int updateById(Receiver receiver);

    @Delete("delete from tb_receiver where id=#{id}")
    int deleteById(int id);

    @Select("select count(id) from tb_receiver")
    int getCount();

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where name like #{name} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getByName(@Param("name") String name, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where name like #{name}")
    int getCountByName(@Param("name") String name);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where mail like #{mail} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getByMail(@Param("mail") String mail, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where mail like #{mail}")
    int getCountByMail(@Param("mail") String mail);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where phone like #{phone} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getByPhone(@Param("phone") String phone, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where phone like #{phone}")
    int getCountByPhone(@Param("phone") String phone);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where wechat like #{wechat} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getByWechat(@Param("wechat") String wechat, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where wechat like #{wechat}")
    int getCountByWechat(@Param("wechat") String wechat);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where qq like #{qq} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getByQq(@Param("qq") String qq, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where qq like #{qq}")
    int getCountByQq(@Param("qq") String qq);
}
