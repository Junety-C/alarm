package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.common.Configuration;
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
    protected boolean send(String message) {
        try {
            logger.debug("start send qq alarm, body:{}", message);
            QueueMessage queueMessage = JSON.parseObject(message, QueueMessage.class);
            client.send(queueMessage.getReceivers(), queueMessage.getContent());
            this.markDeliveryStatus(queueMessage.getLogId(), channel);
            return true;
        } catch (Exception e) {
            logger.error("handle mail message error, caused by", e);
            return false;
        }
    }

    public static void main(String[] args) {
        QQClient qqClient = new QQClient("qq", Configuration.QQ_QUEUE);
        qqClient.start();
    }
}
