package cn.junety.alarm.sender.wechat.entity;

/**
 * Created by caijt on 2017/3/28.
 */
public class ApiURL {

    public static final String GET_UUID_URL = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb" +
                                    "&redirect_uri=https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage" +
                                    "&fun=new&lang=zh_CN&_={1}";

    public static final String GET_QR_CODE_URL = "https://login.weixin.qq.com/qrcode/{uuid}";

    public static final String VERIFY_QR_CODE_URL = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login" +
                                                    "?tip={tip}&uuid={uuid}&_={time}";

    public static final String WECHAT_INIT_URL = "{baseUri}/webwxinit?r={time}&pass_ticket={passTicket}&skey={skey}";

    public static final String GET_CONTACT_URL = "{baseUri}/webwxgetcontact?pass_ticket={passTicket}&skey={skey}&r={time}";

    public static final String SEND_MESSAGE_URL = "{baseUri}/webwxsendmsg?lang=zh_CN&pass_ticket={passTicket}";
}