package cn.junety.alarm.sender.dao;

import org.apache.ibatis.annotations.*;


/**
 * Created by caijt on 2017/1/28.
 */
public interface AlarmLogDao {
    @Update("update tb_alarm_log set status=#{status}, delivery_status=concat(delivery_status, #{channel}, ',') where id=#{logId}")
    int updateDeliveryStatus(@Param("status") int status, @Param("channel") String channel, @Param("logId") long logId);
}
