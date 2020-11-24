package cn.junety.alarm.sender.client;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.sender.client.impl.*;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/5.
 */
public class ClientFactory {

    public static MailClient buildMailClient(String name) {
        return new MailClient(name, ConfigKey.MAIL_QUEUE.value());
    }

    public static QQClient buildQQClient(String name) {
        return new QQClient(name, ConfigKey.QQ_QUEUE.value());
    }

    public static WechatClient buildWechatClient(String name) {
        return new WechatClient(name, ConfigKey.WECHAT_QUEUE.value());
    }

    public static SmsClient buildSmsClient(String name) {
        return new SmsClient(name,ConfigKey.SMS_QUEUE.value());
    }

    public static DeliveryStatusClient buildDeliveryStatusClient(String name, ApplicationContext context) {
        return new DeliveryStatusClient(name, ConfigKey.DELIVERY_QUEUE.value(), context);
    }
}
