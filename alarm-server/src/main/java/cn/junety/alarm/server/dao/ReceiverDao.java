package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Receiver;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by caijt on 2017/1/28.
 */
public interface ReceiverDao {
    @Select("select tu.id, tu.name, tu.mail, tu.phone, tu.wechat, tu.qq from tb_user tu, tb_group_member tgm " +
            "where tu.id=tgm.user_id and tgm.group_id=#{groupId}")
    List<Receiver> getReceiverByGroupId(int groupId);
}
