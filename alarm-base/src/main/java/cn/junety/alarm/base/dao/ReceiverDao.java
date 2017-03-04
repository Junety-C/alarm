package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Receiver;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by caijt on 2017/1/28.
 */
public interface ReceiverDao {
    //@Select("select id, name, phone, mail, wechat, qq from tb_receiver where id")
    @Select("select re.* from tb_receiver re left join tb_group_member gm on re.id=gm.receiver_id and gm.group_id=#{group_id} ")
    List<Receiver> get(int groupId);
}
