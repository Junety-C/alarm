package cn.junety.alarm.base.common;

/**
 * Created by caijt on 2017/4/6.
 */
public enum ConfigKey {

    MAIL_QUEUE("mail.queue"),
    SMS_QUEUE("sms.queue"),
    WECHAT_QUEUE("wechat.queue"),
    QQ_QUEUE("qq.queue"),
    DELIVERY_QUEUE("delivery.queue"),
    ALARM_REPORT_ID_POOL("alarm.report.id.pool"),

    TOTAL_REQUEST_QUANTITY("monitor.request.quantity"),
    MAIL_PUSH_QUANTITY("monitor.mail.push.quantity"),
    SMS_PUSH_QUANTITY("monitor.sms.push.quantity"),
    QQ_PUSH_QUANTITY("monitor.qq.push.quantity"),
    WECHAT_PUSH_QUANTITY("monitor.wechat.push.quantity"),
    MAIL_PUSH_DAILY("monitor.mail.push.{date}"),
    SMS_PUSH_DAILY("monitor.sms.push.{date}"),
    QQ_PUSH_DAILY("monitor.qq.push.{date}"),
    WECHAT_PUSH_DAILY("monitor.wechat.push.{date}");

    private String value;

    ConfigKey(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
