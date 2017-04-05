package cn.junety.alarm.server.configuration;

/**
 * Created by caijt on 2017/1/28.
 */
public class Configuration {

    public static final String MAIL_REDIS_QUEUE = "mail.queue";
    public static final String SMS_REDIS_QUEUE = "sms.queue";
    public static final String WECHAT_REDIS_QUEUE = "wechat.queue";
    public static final String QQ_REDIS_QUEUE = "qq.queue";
    public static final String ALARM_REPORT_ID_POOL = "alarm.report.id.pool";

    public static final String MONITOR_MAIL_TOTAL = "monitor.mail.total";
    public static final String MONITOR_SMS_TOTAL = "monitor.sms.total";
    public static final String MONITOR_QQ_TOTAL = "monitor.qq.total";
    public static final String MONITOR_WECHAT_TOTAL = "monitor.wechat.total";

    public static final String MONITOR_MAIL_DAILY = "monitor.mail.{day}";
    public static final String MONITOR_SMS_DAILY = "monitor.sms.{day}";
    public static final String MONITOR_QQ_DAILY = "monitor.qq.{day}";
    public static final String MONITOR_WECHAT_DAILY = "monitor.wechat.{day}";

}
