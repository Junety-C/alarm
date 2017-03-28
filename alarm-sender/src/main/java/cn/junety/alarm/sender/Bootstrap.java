package cn.junety.alarm.sender;

import cn.junety.alarm.sender.client.ClientFactory;
import cn.junety.alarm.sender.common.Configure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/4.
 */
@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        //if(args.length < 2) {
        //    throw new IllegalArgumentException("invalid args");
        //}
        //String channel = args[0];
        //String name = args[1];

        String channel = "mail";
        String name = "mail_1";

        switch (channel) {
            case "mail":
                ClientFactory.buildMailClient(name).start();
                break;
            case "sms":
                break;
            case "wechat":
                break;
            case "delivery":
                SpringApplication app = new SpringApplication(Bootstrap.class);
                app.setWebEnvironment(false);
                ApplicationContext context = app.run(args);
                ClientFactory.buildDeliveryStatusClient(name, context).start();
                break;
            default:
                throw new IllegalArgumentException("invalid channel");
        }
    }
}
