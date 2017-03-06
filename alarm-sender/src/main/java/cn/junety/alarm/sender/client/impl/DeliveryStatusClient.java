package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.dao.AlarmLogDao;
import cn.junety.alarm.base.entity.DeliveryStatus;
import cn.junety.alarm.sender.client.Client;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/5.
 */
public class DeliveryStatusClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    private AlarmLogDao alarmLogDao;

    public DeliveryStatusClient(String name, String queueName, ApplicationContext context) {
        super(name, queueName, "delivery");
        this.alarmLogDao = context.getBean(AlarmLogDao.class);
    }

    @Override
    protected boolean send(String message) {
        try {
            DeliveryStatus deliveryStatus = JSON.parseObject(message, DeliveryStatus.class);
            return alarmLogDao.updateDeliveryStatus(deliveryStatus) > 0;
        } catch (Exception e) {
            logger.error("send delivery status error, message:{}, caused by", message, e);
        }
        return false;
    }
}
