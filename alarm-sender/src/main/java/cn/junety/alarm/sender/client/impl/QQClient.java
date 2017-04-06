package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.smartqq.SmartqqClientProxy;
import com.alibaba.fastjson.JSON;

/**
 * Created by caijt on 2017/3/28.
 */
public class QQClient extends Client {

    private SmartqqClientProxy client;

    public QQClient(String name, String queueName) {
        super(name, queueName, "qq");
        client = new SmartqqClientProxy();
    }

    @Override
    protected int send(String message) {
        try {
            logger.debug("start send qq alarm, body:{}", message);
            QueueMessage queueMessage = JSON.parseObject(message, QueueMessage.class);
            client.send(queueMessage.getReceivers(), queueMessage.getContent());
            this.markDeliveryStatus(queueMessage.getLogId(), channel);
            return queueMessage.getReceivers().size();
        } catch (Exception e) {
            logger.error("handle mail message error, caused by", e);
            return 0;
        }
    }

    @Override
    protected String getPushQuantityKey() {
        return ConfigKey.QQ_PUSH_QUANTITY.value();
    }

    @Override
    protected String getPushDailyKey() {
        return ConfigKey.QQ_PUSH_DAILY.value();
    }

    public static void main(String[] args) {
        QQClient qqClient = new QQClient("qq", ConfigKey.QQ_QUEUE.value());
        qqClient.start();
    }
}
