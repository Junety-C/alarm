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

    @Select("select id, name, phone, mail, wechat, qq from tb_receiver limit #{begin}, #{length}")
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
    int count();
}
