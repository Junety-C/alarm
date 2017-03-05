package cn.junety.alarm.sender.mail;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.common.ConsumerHandler;
import cn.junety.alarm.sender.common.DeliveryStatusHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caijt on 2017/3/4.
 */
public class MailConsumerHandler implements ConsumerHandler {

    private static final Logger logger = LoggerFactory.getLogger(MailConsumerHandler.class);

    @Override
    public boolean handle(QueueMessage queueMessage) {
        try {
            if(MailClient.send(queueMessage.getTitle(), queueMessage.getContent(), queueMessage.getReceivers())) {
                DeliveryStatusHelper.mark(queueMessage.getLogId(), "mail");
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("handle mail message error, caused by", e);
            return false;
        }
    }
}
