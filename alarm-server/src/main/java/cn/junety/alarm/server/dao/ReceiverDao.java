package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Receiver;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by caijt on 2017/1/28.
 */
public interface ReceiverDao {
    @Select("select tr.id, tr.name, tr.mail, tr.phone, tr.wechat, tr.qq " +
            "from tb_receiver tr, tb_group_member tgm " +
            "where tgm.group_id=#{groupId} and tgm.receiver_id=tr.id")
    List<Receiver> getReceiverByGroupId(int groupId);
}
