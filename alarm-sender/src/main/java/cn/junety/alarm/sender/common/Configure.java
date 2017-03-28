package cn.junety.alarm.sender.common;

import cn.junety.alarm.base.util.properties.Config;
import cn.junety.alarm.base.util.properties.Key;
import cn.junety.alarm.base.util.properties.PropertiesLoader;

/**
 * Created by caijt on 2017/3/4.
 */
@Config("alarm-config.properties")
public class Configure {
    public static final String WECHAT_ACCESS_TOKEN_KEY = "wechat.access.token";

    public static final String MAIL_QUEUE = "mail.queue";
    public static final String QQ_QUEUE = "qq.queue";
    public static final String SMS_QUEUE = "sms.queue";
    public static final String WECHAT_QUEUE = "wechat.queue";
    public static final String DELIVERY_QUEUE = "delivery.queue";

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
    //@Key("sms.api")
    //public static String SMS_API;
    //@Key("sms.backup.api")
    //public static String SMS_BACKUP_API;
    //@Key("wechat.corp.id")
    //public static String WECHAT_CORP_ID;
    //@Key("wechat.corp.secret")
    //public static String WECHAT_CORP_SECRET;
    //@Key("wechat.agent.id")
    //public static Integer WECHAT_AGENT_ID;
    //@Key("wechat.agent.blocker.id")
    //public static Integer WECHAT_AGENT_BLOCKER_ID;
    //@Key("wechat.agent.critial.id")
    //public static Integer WECHAT_AGENT_CRITIAL_ID;
    //@Key("wechat.agent.major.id")
    //public static Integer WECHAT_AGENT_MAJOR_ID;
    //@Key("wechat.agent.minor.id")
    //public static Integer WECHAT_AGENT_MINOR_ID;
    //@Key("wechat.agent.trivial.id")
    //public static Integer WECHAT_AGENT_TRIVIAL_ID;

    static {
        PropertiesLoader.init(Configure.class);
    }
}
