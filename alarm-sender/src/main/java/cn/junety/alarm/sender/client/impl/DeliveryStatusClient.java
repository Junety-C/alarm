package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caijt on 2017/3/5.
 */
public class DeliveryStatusClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    private static final String CHANNEL = "delivery";

    public DeliveryStatusClient(String queueName) {
        super(queueName);
    }

    @Override
    protected boolean send(QueueMessage queueMessage) {
        return false;
    }
}
