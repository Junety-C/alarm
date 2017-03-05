package cn.junety.alarm.sender.client;

import cn.junety.alarm.sender.client.impl.MailClient;
import cn.junety.alarm.sender.common.Configure;

/**
 * Created by caijt on 2017/3/5.
 */
public class ClientFactory {

    public static MailClient buildMailClient(String name) {
        System.setProperty("log.home", name);
        return new MailClient(Configure.MAIL_REDIS_QUEUE);
    }

}
