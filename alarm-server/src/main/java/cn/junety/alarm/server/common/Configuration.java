package cn.junety.alarm.server.common;

import cn.junety.alarm.base.util.properties.Config;
import cn.junety.alarm.base.util.properties.Key;
import cn.junety.alarm.base.util.properties.PropertiesLoader;

/**
 * Created by caijt on 2017/1/28.
 */
@Config("alarm.properties")
public class Configuration {

    public static final String MAIL_REDIS_QUEUE = "mail.queue";
    public static final String SMS_REDIS_QUEUE = "sms.queue";
    public static final String WECHAT_REDIS_QUEUE = "wechat.queue";
    public static final String QQ_REDIS_QUEUE = "qq.queue";
    public static final String ALARM_REPORT_ID_POOL = "alarm.report.id.pool";

    static {
        PropertiesLoader.init(Configuration.class);
    }
}
