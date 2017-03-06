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
        SpringApplication app = new SpringApplication(Bootstrap.class);
        app.setWebEnvironment(false);
        ApplicationContext context = app.run(args);
        ClientFactory.buildDeliveryStatusClient("delivery", Configure.DELIVERY_QUEUE, context).start();

        //if(args.length < 2) {
        //    throw new IllegalArgumentException("invalid args");
        //}
        //String channel = args[0];
        //String name = args[1];
//
        //switch (channel) {
        //    case "mail":
        //        ClientFactory.buildMailClient(name, Configure.MAIL_QUEUE).start();
        //        break;
        //    case "sms":
        //        break;
        //    case "wechat":
        //        break;
        //    case "delivery":
        //        SpringApplication app = new SpringApplication(Bootstrap.class);
        //        app.setWebEnvironment(false);
        //        ApplicationContext context = app.run(args);
        //        ClientFactory.buildDeliveryStatusClient(name, Configure.DELIVERY_QUEUE, context).start();
        //        break;
        //    default:
        //        throw new IllegalArgumentException("invalid channel");
        //}
    }
}
