package cn.junety.alarm.sender;

import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.client.ClientFactory;
import cn.junety.alarm.sender.common.Configure;
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

        switch (channel) {
            case "mail":
                ClientFactory.buildMailClient(name).start();
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
