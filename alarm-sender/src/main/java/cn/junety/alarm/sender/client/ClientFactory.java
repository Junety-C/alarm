package cn.junety.alarm.sender.client;

import cn.junety.alarm.sender.client.impl.DeliveryStatusClient;
import cn.junety.alarm.sender.client.impl.MailClient;
import cn.junety.alarm.sender.client.impl.QQClient;
import cn.junety.alarm.sender.client.impl.WechatClient;
import cn.junety.alarm.sender.common.Configure;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/5.
 */
public class ClientFactory {

    public static MailClient buildMailClient(String name) {
        System.setProperty("log.home", name);
        return new MailClient(name, Configure.MAIL_QUEUE);
    }

    public static QQClient buildQQClient(String name) {
        System.setProperty("log.home", name);
        return new QQClient(name, Configure.QQ_QUEUE);
    }

    public static WechatClient buildWechatClient(String name) {
        System.setProperty("log.home", name);
        return new WechatClient(name, Configure.WECHAT_QUEUE);
    }

    public static DeliveryStatusClient buildDeliveryStatusClient(String name, ApplicationContext context) {
        System.setProperty("log.home", name);
        return new DeliveryStatusClient(name, Configure.DELIVERY_QUEUE, context);
    }
}
