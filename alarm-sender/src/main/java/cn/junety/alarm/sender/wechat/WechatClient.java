package cn.junety.alarm.sender.wechat;

import cn.junety.alarm.sender.wechat.entity.ApiURL;
import cn.junety.alarm.sender.wechat.entity.WechatApiParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import net.dongliu.requests.Client;
import net.dongliu.requests.HeadOnlyRequestBuilder;
import net.dongliu.requests.Response;
import net.dongliu.requests.Session;
import net.dongliu.requests.struct.Cookie;
import net.dongliu.requests.struct.Cookies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by caijt on 2017/3/28.
 */
public class WechatClient implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(WechatClient.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
    private static Random random = new Random();

    private Client client;
    private Session session;

    private WechatApiParams params = new WechatApiParams();

    private JSONObject accountInfo;

    public WechatClient() {
        System.setProperty ("jsse.enableSNIExtension", "false");
        this.client = Client.pooled().maxPerRoute(5).maxTotal(10).build();
        this.session = client.session();
        login();
    }

    private void login() {
        String uuid = getUUID();
        params.setUuid(uuid);
        params.setDeviceId("e" + System.currentTimeMillis() + String.format("%02d", random.nextInt(100)));
        getQRCode();
        verifyQRCode();
        getApiParams();
        initWechat();
    }

    // 登录流程1: 获取UUID
    private String getUUID() {
        String response = get(ApiURL.GET_UUID_URL.replace("{1}", ""+System.currentTimeMillis())).getBody();
        if(Strings.isNullOrEmpty(response)) {
            throw new NullPointerException("获取UUID失败, response为空");
        }
        String code = match("window.QRLogin.code = (\\d+);", response);
        if("200".equals(code)) {
            return match("window.QRLogin.uuid = \"(.*)\";", response);
        } else {
            logger.info("错误的状态码: {}", code);
            throw new RuntimeException(String.format("错误的状态码: %s", code));
        }
    }

    // 登录流程2: 获取二维码
    private void getQRCode() {
        logger.debug("开始获取二维码");

        //本地存储二维码图片
        String filePath;
        try {
            filePath = new File("/Users/caijt/Desktop/wechat-qrcode.png").getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException("二维码保存失败");
        }
        session.get(ApiURL.GET_QR_CODE_URL.replace("{uuid}", params.getUuid()))
                .addHeader("User-Agent", USER_AGENT)
                .file(filePath);
        logger.info("二维码已保存在 " + filePath + " 文件中，请打开微信并扫描二维码");

        //使用默认软件打开二维码
        Desktop desk = Desktop.getDesktop();
        try {
            File file = new File(filePath);
            desk.open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 登录流程3: 校验二维码
    private void verifyQRCode() {
        logger.debug("等待扫描二维码");
        while (true) {
            try {
                sleep(3);
                String response = get(ApiURL.VERIFY_QR_CODE_URL.replace("{tip}", "1")
                                    .replace("{uuid}", params.getUuid()
                                    .replace("{_}", System.currentTimeMillis() / 1000 + "")))
                                    .getBody();
                String code = match("window.code=(\\d+);", response);
                if ("201".equals(code)) {
                    logger.info("成功扫描,请在手机上点击确认以登录");
                } else if ("200".equals(code)) {
                    logger.info("正在登录...");
                    String uri = match("window.redirect_uri=\"(\\S+?)\";", response);
                    String redirectUri = uri + "&fun=new&version=v2";
                    String baseUri = redirectUri.substring(0, redirectUri.lastIndexOf("/"));
                    params.setRedirectUri(redirectUri);
                    params.setBaseUri(baseUri);
                    return;
                } else if ("408".equals(code)) {
                    logger.info("登录超时");
                    throw new RuntimeException("登录超时");
                } else {
                    logger.info("未知错误, code:{}", code);
                }
            } catch (Exception e) {
                logger.debug("verify QR Code error, caused by", e);
            }
        }
    }

    // 登录流程4：获取登录参数
    private void getApiParams() {
        Response<String> response = session.get(params.getRedirectUri()).text();
        Cookies cookies = response.getCookies();
        String cookieStr = "";
        for (Cookie cookie : cookies) {
            cookieStr += cookie.getName() + "=" + cookie.getValue();
        }
        params.setCookies(cookieStr);

        String res = response.getBody();
        String skey = match("<skey>(\\S+)</skey>", res);
        String wxsid = match("<wxsid>(\\S+)</wxsid>", res);
        String wxuin = match("<wxuin>(\\S+)</wxuin>", res);
        String passTicket = match("<pass_ticket>(\\S+)</pass_ticket>", res);

        params.setSkey(skey);
        params.setWxsid(wxsid);
        params.setWxuin(wxuin);
        params.setPassTicket(passTicket);

        JSONObject baseRequest = new JSONObject();
        baseRequest.put("Uin", wxuin);
        baseRequest.put("Sid", wxsid);
        baseRequest.put("Skey", skey);
        baseRequest.put("DeviceID", params.getDeviceId());
        params.setBaseRequest(baseRequest);
    }

    // 登录流程5：微信初始化
    private void initWechat(){
        String url = ApiURL.WECHAT_INIT_URL.replace("{baseUri}", params.getBaseUri())
                                            .replace("{time}", System.currentTimeMillis()/1000+ "")
                                            .replace("{passTicket}", params.getPassTicket())
                                            .replace("{skey}", params.getSkey());
        JSONObject body = new JSONObject();
        body.put("BaseRequest", params.getBaseRequest());
        try {
            String response = post(url, body).getBody();
            JSONObject jsonObject = JSON.parseObject(response);
            JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
            int ret = BaseResponse.getInteger("Ret");
            if (ret == 0) {
                accountInfo = jsonObject.getJSONObject("User");
                logger.info("account info:{}", accountInfo);
            }
        } catch (Exception e) {
            logger.error("init wechat error, caused by", e);
        }
    }

    /**
     * 获取联系人
     */
    public List<JSONObject> getFriendList(){
        String url = ApiURL.GET_CONTACT_URL.replace("{baseUri}", params.getBaseUri())
                                            .replace("{passTicket}", params.getPassTicket())
                                            .replace("{skey}", params.getSkey())
                                            .replace("{time}", System.currentTimeMillis()/1000 + "");
        JSONObject body = new JSONObject();
        body.put("BaseRequest", params.getBaseRequest());
        try {
            String response = post(url, body).getBody();
            JSONObject jsonObject = JSON.parseObject(response);
            JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
            int ret = BaseResponse.getInteger("Ret");
            if(ret == 0){
                JSONArray memberList = jsonObject.getJSONArray("MemberList");
                if(memberList != null){
                    List<JSONObject> friends = new ArrayList<>();
                    for(int i = 0; i < memberList.size(); i++) {
                        JSONObject contact = memberList.getJSONObject(i);
                        if (contact.getInteger("VerifyFlag") == 0 && !Strings.isNullOrEmpty(contact.getString("Alias"))) {
                            friends.add(contact);
                        }
                    }
                    return friends;
                }
            }
        } catch (Exception e) {
            logger.error("get contact error, caused by", e);
        }
        return Collections.emptyList();
    }

    /**
     * 发送文本消息
     */
    public void sendMessageToFriend(String receiver, String content) {
        try {
            String url = ApiURL.SEND_MESSAGE_URL.replace("{baseUri}", params.getBaseUri())
                    .replace("{passTicket}", params.getPassTicket());
            JSONObject body = new JSONObject();
            String clientMsgId = System.currentTimeMillis() + String.format("%04d", random.nextInt(10000)); //17位数字
            JSONObject msg = new JSONObject();
            msg.put("Type", 1);
            msg.put("Content", content);
            msg.put("FromUserName", accountInfo.getString("UserName"));
            msg.put("ToUserName", receiver);
            msg.put("LocalID", clientMsgId);
            msg.put("ClientMsgId", clientMsgId);
            body.put("BaseRequest", params.getBaseRequest());
            body.put("Msg", msg);
            String response = post(url, body).getBody();
            logger.debug("send message to {}, response:{}", receiver, response);
        } catch (Exception e) {
            logger.error("send message error, receiver:{}, content:{}, caused by", receiver, content, e);
        }
    }

    //发送get请求
    private Response<String> get(String url) {
        HeadOnlyRequestBuilder request = session.get(url)
                .addHeader("User-Agent", USER_AGENT);
        return request.text();
    }

    //发送post请求
    private Response<String> post(String url, JSONObject body) {
        return session.post(url)
                .addHeader("User-Agent", USER_AGENT)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("Cookie", params.getCookies())
                .data(body.toString())
                .text();
    }

    private static String match(String p, String str){
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        if(m.find()){
            return m.group(1);
        }
        return null;
    }

    private static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void close() throws IOException {
        if (this.client != null) {
            this.client.close();
        }
    }
}

