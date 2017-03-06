package cn.junety.alarm.sender.client;

import cn.junety.alarm.sender.client.impl.DeliveryStatusClient;
import cn.junety.alarm.sender.client.impl.MailClient;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/5.
 */
public class ClientFactory {

    public static MailClient buildMailClient(String name, String queueName) {
        System.setProperty("log.home", name);
        return new MailClient(name, queueName);
    }

    public static DeliveryStatusClient buildDeliveryStatusClient(String name, String queueName, ApplicationContext context) {
        System.setProperty("log.home", name);
        return new DeliveryStatusClient(name, queueName, context);
    }
}
