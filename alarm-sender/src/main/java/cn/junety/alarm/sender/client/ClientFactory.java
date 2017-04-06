package cn.junety.alarm.sender.client;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.sender.client.impl.DeliveryStatusClient;
import cn.junety.alarm.sender.client.impl.MailClient;
import cn.junety.alarm.sender.client.impl.QQClient;
import cn.junety.alarm.sender.client.impl.WechatClient;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/5.
 */
public class ClientFactory {

    public static MailClient buildMailClient(String name) {
        System.setProperty("log.home", name);
        return new MailClient(name, ConfigKey.MAIL_QUEUE.value());
    }

    public static QQClient buildQQClient(String name) {
        System.setProperty("log.home", name);
        return new QQClient(name, ConfigKey.QQ_QUEUE.value());
    }

    public static WechatClient buildWechatClient(String name) {
        System.setProperty("log.home", name);
        return new WechatClient(name, ConfigKey.WECHAT_QUEUE.value());
    }

    public static DeliveryStatusClient buildDeliveryStatusClient(String name, ApplicationContext context) {
        System.setProperty("log.home", name);
        return new DeliveryStatusClient(name, ConfigKey.DELIVERY_QUEUE.value(), context);
    }
}
