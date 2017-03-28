package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.common.Configure;
import cn.junety.alarm.sender.smartqq.SmartqqClientProxy;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caijt on 2017/3/28.
 */
public class QQClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(QQClient.class);

    private SmartqqClientProxy client;

    public QQClient(String name, String queueName) {
        super(name, queueName, "qq");
        client = new SmartqqClientProxy();
    }

    @Override
    protected boolean send(String message) {
        try {
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
        QQClient qqClient = new QQClient("qq", Configure.QQ_QUEUE);
        qqClient.start();
    }
}
