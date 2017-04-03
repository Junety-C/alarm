package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Receiver;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/4/3.
 */
public interface ReceiverDao {
    @Select("select tr.id, tr.name, tr.mail, tr.phone, tr.wechat, tr.qq " +
            "from tb_receiver tr, tb_group_member tgm " +
            "where tgm.group_id=#{groupId} and tgm.receiver_id=tr.id")
    List<Receiver> getReceiverByGroupId(int groupId);

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver order by id desc limit #{begin}, #{length}")
    List<Receiver> getReceiver(@Param("begin") int begin, @Param("length") int length);

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver")
    List<Receiver> getAllReceiver();

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver where id=#{id}")
    Receiver getReceiverById(@Param("id") int id);

    @Insert("insert into tb_receiver(name, phone, mail, wechat, qq) values(#{name}, #{phone}, #{mail}, #{wechat}, #{qq})")
    int save(Receiver receiver);

    @Update("update tb_receiver set name=#{name}, phone=#{phone}, mail=#{mail}, wechat=#{wechat}, qq=#{qq} where id=#{id}")
    int updateById(Receiver receiver);

    @Delete("delete from tb_receiver where id=#{id}")
    int deleteById(int id);

    @Select("select count(id) from tb_receiver")
    int getReceiverCount();

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where name like #{name} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getReceiverByName(@Param("name") String name, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where name like #{name}")
    int getReceiverCountByName(@Param("name") String name);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where mail like #{mail} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getReceiverByMail(@Param("mail") String mail, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where mail like #{mail}")
    int getReceiverCountByMail(@Param("mail") String mail);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where phone like #{phone} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getReceiverByPhone(@Param("phone") String phone, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where phone like #{phone}")
    int getReceiverCountByPhone(@Param("phone") String phone);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where wechat like #{wechat} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getReceiverByWechat(@Param("wechat") String wechat, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where wechat like #{wechat}")
    int getReceiverCountByWechat(@Param("wechat") String wechat);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where qq like #{qq} " +
            "order by id desc " +
            "limit #{begin}, #{length}")
    List<Receiver> getReceiverByQq(@Param("qq") String qq, @Param("begin") int begin, @Param("length") int length);

    @Select("select count(id) from tb_receiver where qq like #{qq}")
    int getReceiverCountByQq(@Param("qq") String qq);
}
