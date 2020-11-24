package cn.junety.alarm.sender.configuration;

import cn.junety.alarm.base.util.HttpUtils;
import cn.junety.alarm.base.util.properties.Config;
import cn.junety.alarm.base.util.properties.Key;
import cn.junety.alarm.base.util.properties.PropertiesLoader;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by caijt on 2017/3/4.
 */
@Config("alarm-config.properties")
public class Configuration {

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

    @Key("sms.login.url")
    public static String SMS_LOGIN_URL;

    @Key("sms.logout.url")
    public static String SMS_LOGOUT_URL;
    @Key("sms.send.url")
    public static String SMS_SEND_URL;
    @Key("sms.token")
    public static String SMS_TOKEN;



    static {
        PropertiesLoader.init(Configuration.class);
        String SMS_LOGIN_URL_PARAM = "{\"request\":{\"@id\":\"10000\",\"@type\":\"UserLogin\",\"userName\":\"\",\"userPwd\":\"Bjcsdn@123!\"}}";
        System.out.println("end:"+SMS_LOGIN_URL_PARAM);
        String res = HttpUtils.sendPost(Configuration.SMS_LOGIN_URL,SMS_LOGIN_URL_PARAM);
        System.out.println(res);
        Map mapType = JSON.parseObject(res,Map.class);
        Configuration.SMS_TOKEN = (String)mapType.get("tokensign");


    }
}
