package cn.junety.alarm.sender.configuration;

import cn.junety.alarm.base.util.properties.Config;
import cn.junety.alarm.base.util.properties.Key;
import cn.junety.alarm.base.util.properties.PropertiesLoader;

/**
 * Created by caijt on 2017/3/4.
 */
@Config("alarm-config.properties")
public class Configuration {

    public static final String MAIL_QUEUE = "mail.queue";
    public static final String QQ_QUEUE = "qq.queue";
    public static final String SMS_QUEUE = "sms.queue";
    public static final String WECHAT_QUEUE = "wechat.queue";
    public static final String DELIVERY_QUEUE = "delivery.queue";

    public static final String MONITOR_MAIL_TOTAL = "monitor.mail.total";
    public static final String MONITOR_SMS_TOTAL = "monitor.sms.total";
    public static final String MONITOR_QQ_TOTAL = "monitor.qq.total";
    public static final String MONITOR_WECHAT_TOTAL = "monitor.wechat.total";

    public static final String MONITOR_MAIL_DAILY = "monitor.mail.{day}";
    public static final String MONITOR_SMS_DAILY = "monitor.sms.{day}";
    public static final String MONITOR_QQ_DAILY = "monitor.qq.{day}";
    public static final String MONITOR_WECHAT_DAILY = "monitor.wechat.{day}";

    @Key("mail.sender.username")
    public static String MAIL_SENDER_USERNAME;
    @Key("mail.sender.password")
    public static String MAIL_SENDER_PASSWORD;
    @Key("mail.sender.smtp.host")
    public static String MAIL_SENDER_SMTP_HOST;
    @Key("mail.sender.smtp.port")
    public static Integer MAIL_SENDER_SMTP_PORT;
    @Key("mail.sender.name")
    public static String MAIL_SENDER_NAME;
    @Key("qq.qrcode.path")
    public static String QQ_QRCODE_PATH;
    @Key("wechat.qrcode.path")
    public static String WECHAT_QRCODE_PATH;

    static {
        PropertiesLoader.init(Configuration.class);
    }
}
