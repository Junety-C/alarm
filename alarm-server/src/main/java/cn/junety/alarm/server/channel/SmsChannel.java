package cn.junety.alarm.server.channel;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.server.vo.AlarmMessage;
import cn.junety.alarm.base.entity.QueueMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

/**
 * Created by caijt on 2017/1/28.
 */
@Service("smsChannel")
public class SmsChannel extends Channel {

    @Override
    public void send(AlarmMessage alarmMessage) {
        String content = "告警名称:" + alarmMessage.getAlarmName()
                + ",上报编号:" + alarmMessage.getReportId()
                + ",时间:" + alarmMessage.getCreatetimeFormat()
                + ",内容:" + alarmMessage.getContent();

        if(content.length() > 500) {
            content = content.substring(0, 480) + "(完整内容见告警日志)";
        }

        QueueMessage queueMessage = new QueueMessage(null, content, alarmMessage.getPhoneList(),
                alarmMessage.getLogId(), alarmMessage.getLevel());
        this.save(JSON.toJSONString(queueMessage));
    }

    @Override
    protected String getPreSendingQueue() {
        return ConfigKey.SMS_QUEUE.value();
    }
}
