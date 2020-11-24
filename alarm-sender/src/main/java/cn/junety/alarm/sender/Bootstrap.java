package cn.junety.alarm.sender;

import cn.junety.alarm.base.util.HttpUtils;
import cn.junety.alarm.sender.client.ClientFactory;
import cn.junety.alarm.sender.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by caijt on 2017/3/4.
 * 启动: java -jar alarm-sender.jar {channel} {name}
 */
@SpringBootApplication
public class Bootstrap {
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
//        if(args.length < 2) {
//            throw new IllegalArgumentException("invalid args");
//        }
//        String channel = args[0];
//        String name = args[1];
        String channel = "sms";
        String name = "sms";

        System.setProperty("log.home", name);

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
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        String res = HttpUtils.sendGet("http://10.32.40.21:9005/idispatch/keepconnect",Configuration.SMS_TOKEN);
                        logger.info("sms_keepAlive:"+res);
                    }
                }, 3000,10000);

                ClientFactory.buildSmsClient(name).start();



                Runtime.getRuntime().addShutdownHook(
                        new Thread() {
                            public void run() {
                                String res = HttpUtils.sendGet(Configuration.SMS_LOGOUT_URL,Configuration.SMS_TOKEN);
                                logger.info("sms_logout:"+res);
                            }
                        }
                );
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
