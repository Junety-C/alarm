package cn.junety.alarm.sender;

import cn.junety.alarm.sender.client.ClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by caijt on 2017/3/4.
 * 启动: java -jar alarm-sender.jar {channel} {name}
 */
@SpringBootApplication
public class Bootstrap {

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
            case "wechat":
                ClientFactory.buildWechatClient(name).start();
                break;
            case "qq":
                ClientFactory.buildQQClient(name).start();
                break;
            case "sms":
                throw new RuntimeException("没钱发啊, 不支持了!");
                // break;
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
