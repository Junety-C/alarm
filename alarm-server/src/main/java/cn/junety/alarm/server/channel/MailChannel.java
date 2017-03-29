package cn.junety.alarm.server.channel;

import cn.junety.alarm.server.common.Configuration;
import cn.junety.alarm.server.vo.AlarmMessage;
import cn.junety.alarm.base.entity.QueueMessage;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by caijt on 2017/1/28.
 */
@Service("mailChannel")
public class MailChannel extends Channel {

    private static final Logger logger = LoggerFactory.getLogger(MailChannel.class);

    private static final String NEW_LINE = "<br/>";

    @Override
    public void send(AlarmMessage alarmMessage) {
        String title = "告警:" + alarmMessage.getProjectName() + "-" + alarmMessage.getModuleName()
                + "-" + alarmMessage.getAlarmName();
        String content = "上报编号:" + alarmMessage.getReportId() + NEW_LINE
                + "项目:" + alarmMessage.getProjectName() + NEW_LINE
                + "模块:" + alarmMessage.getModuleName() + NEW_LINE
                + "告警名称:" + alarmMessage.getAlarmName() + NEW_LINE
                + "告警级别:" + alarmMessage.getLevel().name() + NEW_LINE
                + "告警IP:" + alarmMessage.getIp() + NEW_LINE
                + "告警时间:" + alarmMessage.getCreatetimeFormat() + NEW_LINE
                + "告警内容:" + alarmMessage.getContent();

        QueueMessage queueMessage = new QueueMessage(title, content, alarmMessage.getMailList(),
                alarmMessage.getLogId(), alarmMessage.getLevel());
        this.save(JSON.toJSONString(queueMessage));
    }

    @Override
    protected String getPreSendingQueue() {
        return Configuration.MAIL_REDIS_QUEUE;
    }
}
