package cn.junety.alarm.sender.common;

import cn.junety.alarm.base.util.properties.Config;
import cn.junety.alarm.base.util.properties.Key;
import cn.junety.alarm.base.util.properties.PropertiesLoader;

/**
 * Created by caijt on 2017/3/4.
 */
@Config("alarm_center.properties")
public class Configure {
    public static final String WECHAT_ACCESS_TOKEN_KEY = "wechat.access.token";
    public static final int MAX_RESEND_TIMES = 5;

    public static final String EMAIL_REDIS_QUEUE = "email.queue";
    public static final String SMS_REDIS_QUEUE = "sms.queue";
    public static final String WECHAT_REDIS_QUEUE = "wechat.queue";
    public static final String DELIVERY_WRITER_QUEUE = "delivery.writer.queue";

    public static final String WECHAT_TRANSMIT_QUEUE = "wechat.transmit.queue";
    public static final String WECHAT_REDIS_ZSET = "wechat.zset";
    public static final String WECHAT_REDIS_COLLECTION_QUEUE = "wechat.collection.queue";
    public static final String SMS_TRANSMIT_QUEUE = "sms.transmit.queue";
    public static final String SMS_REDIS_ZSET = "sms.zset";
    public static final String SMS_REDIS_COLLECTION_QUEUE = "sms.collection.queue";
    public static final String EMAIL_TRANSMIT_QUEUE = "email.transmit.queue";
    public static final String EMAIL_REDIS_ZSET = "email.zset";
    public static final String EMAIL_REDIS_COLLECTION_QUEUE = "email.collection.queue";

    @Key("mail.sender")
    public static String MAIL_SENDER;
    @Key("mail.sender.password")
    public static String MAIL_SENDER_PASSWORD;
    @Key("mail.sender.smtp.host")
    public static String MAIL_SENDER_SMTP_HOST;
    @Key("mail.sender.smtp.port")
    public static Integer MAIL_SENDER_SMTP_PORT;
    @Key("mail.sender.name")
    public static String MAIL_SENDER_NAME;
    @Key("mail.send_cloud.appkey")
    public static String MAIL_SEND_CLOUD_APPKEY;
    @Key("mail.send_cloud.secret")
    public static String MAIL_SEND_CLOUD_SECRET;
    @Key("sms.api")
    public static String SMS_API;
    @Key("sms.backup.api")
    public static String SMS_BACKUP_API;
    @Key("wechat.corp.id")
    public static String WECHAT_CORP_ID;
    @Key("wechat.corp.secret")
    public static String WECHAT_CORP_SECRET;
    @Key("wechat.agent.id")
    public static Integer WECHAT_AGENT_ID;
    @Key("backup.receiver.wechat")
    public static String BACKUP_RECEIVER_WECHAT;
    @Key("backup.receiver.phone")
    public static String BACKUP_RECEIVER_PHONE;
    @Key("wechat.agent.blocker.id")
    public static Integer WECHAT_AGENT_BLOCKER_ID;
    @Key("wechat.agent.critial.id")
    public static Integer WECHAT_AGENT_CRITIAL_ID;
    @Key("wechat.agent.major.id")
    public static Integer WECHAT_AGENT_MAJOR_ID;
    @Key("wechat.agent.minor.id")
    public static Integer WECHAT_AGENT_MINOR_ID;
    @Key("wechat.agent.trivial.id")
    public static Integer WECHAT_AGENT_TRIVIAL_ID;

    static {
        PropertiesLoader.init(Configure.class);
    }
}
