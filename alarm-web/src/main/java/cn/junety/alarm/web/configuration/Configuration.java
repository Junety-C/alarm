package cn.junety.alarm.web.configuration;

/**
 * Created by caijt on 2017/1/28.
 */
public class Configuration {
    public static final String MAIL_REDIS_QUEUE = "mail.queue";
    public static final String SMS_REDIS_QUEUE = "sms.queue";
    public static final String WECHAT_REDIS_QUEUE = "wechat.queue";
    public static final String QQ_REDIS_QUEUE = "qq.queue";

    public static final String MAIL_PUSH_QUANTITY = "monitor.mail.push.quantity";
    public static final String SMS_PUSH_QUANTITY = "monitor.sms.push.quantity";
    public static final String QQ_PUSH_QUANTITY = "monitor.qq.push.quantity";
    public static final String WECHAT_PUSH_QUANTITY = "monitor.wechat.push.quantity";

    public static final String MAIL_PUSH_DAILY = "monitor.mail.push.{date}";
    public static final String SMS_PUSH_DAILY = "monitor.sms.push.{date}";
    public static final String QQ_PUSH_DAILY = "monitor.qq.push.{date}";
    public static final String WECHAT_PUSH_DAILY = "monitor.wechat.push.{date}";
}
