package cn.junety.alarm.server.channel;

import cn.junety.alarm.server.common.Configuration;
import cn.junety.alarm.server.vo.AlarmMessage;
import org.springframework.stereotype.Service;

/**
 * Created by caijt on 2017/1/28.
 */
@Service("qqChannel")
public class QQChannel extends Channel {

    @Override
    public void send(AlarmMessage alarmMessage) {
        System.out.println("Sorry, nonsupport send by QQ");
    }

    @Override
    protected String getPreSendingQueue() {
        return Configuration.QQ_REDIS_QUEUE;
    }
}
