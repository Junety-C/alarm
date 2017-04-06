package cn.junety.alarm.server.channel;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.server.vo.AlarmMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

/**
 * Created by caijt on 2017/1/28.
 */
@Service("qqChannel")
public class QQChannel extends Channel {

    private static final String NEW_LINE = "\n";

    @Override
    public void send(AlarmMessage alarmMessage) {
        String content = "告警:" + alarmMessage.getAlarmName() + NEW_LINE
                + "上报编号:" + alarmMessage.getReportId() + NEW_LINE
                + "项目-模块:" + alarmMessage.getProjectName() + "-" + alarmMessage.getModuleName() + NEW_LINE
                + "级别:" + alarmMessage.getLevel().name() + NEW_LINE
                + "IP:" + alarmMessage.getIp() + NEW_LINE
                + "时间:" + alarmMessage.getCreatetimeFormat() + NEW_LINE
                + "内容:" + alarmMessage.getContent();

        if (content.length() > 500) {
            content = content.substring(0, 480) + "(完整内容见告警日志)";
        }

        QueueMessage queueMessage = new QueueMessage(null, content, alarmMessage.getQQList(),
                alarmMessage.getLogId(), alarmMessage.getLevel());
        this.save(JSON.toJSONString(queueMessage));
    }

    @Override
    protected String getPreSendingQueue() {
        return ConfigKey.QQ_QUEUE.value();
    }
}
