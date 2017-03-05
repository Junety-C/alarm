package cn.junety.alarm.sender;

import cn.junety.alarm.sender.common.Configure;
import cn.junety.alarm.sender.common.Consumer;
import cn.junety.alarm.sender.mail.MailConsumerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caijt on 2017/3/4.
 */
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("invalid args");
        }
        String channel = args[0];
        String name = args[1];
        System.setProperty("log.home", name);

        switch (channel) {
            case "mail":
                new Thread(new Consumer(new MailConsumerHandler(), Configure.SMS_REDIS_QUEUE)).start();
                break;
            case "sms":
                break;
            case "wechat":
                break;
            case "delivery":
                break;
            default:
                throw new IllegalArgumentException("invalid channel");
        }
    }
}
