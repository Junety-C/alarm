package cn.junety.alarm.web.dao;

import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.vo.ReceiverSearch;
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

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver")
    List<Receiver> getAllReceiver();

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver where id=#{id}")
    Receiver getReceiverById(@Param("id") int id);

    @Select("select receiver_id from tb_user_to_receiver where user_id=#{uid}")
    int getReceiverIdByUserId(@Param("uid") int uid);

    @Insert("insert into tb_receiver(name, phone, mail, wechat, qq) values(#{name}, #{phone}, #{mail}, #{wechat}, #{qq})")
    int save(Receiver receiver);

    @Update("update tb_receiver set name=#{name}, phone=#{phone}, mail=#{mail}, wechat=#{wechat}, qq=#{qq} where id=#{id}")
    int updateById(Receiver receiver);

    @Delete("delete from tb_receiver where id=#{id}")
    int deleteById(int id);


    @Select("select id, name, phone, mail, wechat, qq from tb_receiver order by id desc limit #{page.start}, #{page.pageSize}")
    List<Receiver> getReceiver(ReceiverSearch receiverSearch);

    @Select("select count(id) from tb_receiver")
    int getReceiverCount();

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where name like '${name}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Receiver> getReceiverByName(ReceiverSearch receiverSearch);

    @Select("select count(id) from tb_receiver where name like '${name}%'")
    int getReceiverCountByName(ReceiverSearch receiverSearch);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where mail like '${mail}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Receiver> getReceiverByMail(ReceiverSearch receiverSearch);

    @Select("select count(id) from tb_receiver where mail like '${mail}%'")
    int getReceiverCountByMail(ReceiverSearch receiverSearch);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where phone like '${phone}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Receiver> getReceiverByPhone(ReceiverSearch receiverSearch);

    @Select("select count(id) from tb_receiver where phone like '${phone}%'")
    int getReceiverCountByPhone(ReceiverSearch receiverSearch);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where wechat like '${wechat}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Receiver> getReceiverByWechat(ReceiverSearch receiverSearch);

    @Select("select count(id) from tb_receiver where wechat like '${wechat}%'")
    int getReceiverCountByWechat(ReceiverSearch receiverSearch);

    @Select("select id, name, phone, mail, wechat, qq " +
            "from tb_receiver " +
            "where qq like '${qq}%' " +
            "order by id desc " +
            "limit #{page.start}, #{page.pageSize}")
    List<Receiver> getReceiverByQq(ReceiverSearch receiverSearch);

    @Select("select count(id) from tb_receiver where qq like '${qq}%'")
    int getReceiverCountByQq(ReceiverSearch receiverSearch);
}
