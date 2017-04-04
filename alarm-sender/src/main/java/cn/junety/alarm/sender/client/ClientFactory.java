package cn.junety.alarm.sender.client;

import cn.junety.alarm.sender.client.impl.DeliveryStatusClient;
import cn.junety.alarm.sender.client.impl.MailClient;
import cn.junety.alarm.sender.client.impl.QQClient;
import cn.junety.alarm.sender.client.impl.WechatClient;
import cn.junety.alarm.sender.common.Configuration;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/5.
 */
public class ClientFactory {

    public static MailClient buildMailClient(String name) {
        System.setProperty("log.home", name);
        return new MailClient(name, Configuration.MAIL_QUEUE);
    }

    public static QQClient buildQQClient(String name) {
        System.setProperty("log.home", name);
        return new QQClient(name, Configuration.QQ_QUEUE);
    }

    public static WechatClient buildWechatClient(String name) {
        System.setProperty("log.home", name);
        return new WechatClient(name, Configuration.WECHAT_QUEUE);
    }

    public static DeliveryStatusClient buildDeliveryStatusClient(String name, ApplicationContext context) {
        System.setProperty("log.home", name);
        return new DeliveryStatusClient(name, Configuration.DELIVERY_QUEUE, context);
    }
}
