package cn.junety.alarm.sender.smartqq;

import cn.junety.alarm.sender.client.impl.QQClient;
import cn.junety.alarm.sender.configuration.Configuration;
import cn.junety.alarm.sender.smartqq.entity.*;
import cn.junety.alarm.sender.smartqq.entity.Font;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.dongliu.requests.Client;
import net.dongliu.requests.HeadOnlyRequestBuilder;
import net.dongliu.requests.Response;
import net.dongliu.requests.Session;
import net.dongliu.requests.exception.RequestException;
import net.dongliu.requests.struct.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by caijt on 2017/3/28.
 * 参考github上smartqq项目
 * <a href="https://github.com/ScienJus/smartqq">smartqq</a>
 */
public class SmartqqClient implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(QQClient.class);

    //消息id，这个好像可以随便设置，所以设成全局的
    private static long MESSAGE_ID = 43690001;

    //客户端id，固定的
    private static final long Client_ID = 53999199;

    //消息发送失败重发次数
    private static final long RETRY_TIMES = 5;

    private Client client;

    private Session session;

    //二维码令牌
    private String qrsig;

    //鉴权参数
    private String ptwebqq;
    private String vfwebqq;
    private long uin;
    private String psessionid;

    public SmartqqClient() {
        this.client = Client.pooled().maxPerRoute(5).maxTotal(10).build();
        this.session = client.session();
        login();
    }

    /**
     * 登录
     */
    private void login() {
        getQRCode();
        String url = verifyQRCode();
        getPtwebqq(url);
        getVfwebqq();
        getUinAndPsessionid();
        getFriendStatus(); //修复Api返回码[103]的问题
        UserInfo userInfo = getAccountInfo();
        logger.info("登录成功, 欢迎:" + userInfo.getNick());
    }

    //登录流程1：获取二维码
    private void getQRCode() {
        logger.debug("开始获取二维码");

        //本地存储二维码图片
        String filePath;
        try {
            filePath = new File(Configuration.QQ_QRCODE_PATH).getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException("二维码保存失败");
        }
        Response response = session.get(ApiURL.GET_QR_CODE.getUrl())
                .addHeader("User-Agent", ApiURL.USER_AGENT)
                .file(filePath);
        for (Cookie cookie : response.getCookies()) {
            if (Objects.equals(cookie.getName(), "qrsig")) {
                qrsig = cookie.getValue();
                break;
            }
        }
        logger.info("二维码已保存在 " + filePath + " 文件中,请打开手机QQ并扫描二维码");

        //使用默认软件打开二维码
        try {
            Desktop desk = Desktop.getDesktop();
            File file = new File(filePath);
            desk.open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用于生成ptqrtoken的哈希函数
    private static int hash33(String s) {
        int e = 0, n = s.length();
        for (int i = 0; n > i; ++i)
            e += (e << 5) + s.charAt(i);
        return 2147483647 & e;
    }

    //登录流程2：校验二维码
    private String verifyQRCode() {
        logger.debug("等待扫描二维码");

        //阻塞直到确认二维码认证成功
        while (true) {
            sleep(1);
            Response<String> response = get(ApiURL.VERIFY_QR_CODE, hash33(qrsig));
            String result = response.getBody();
            if (result.contains("成功")) {
                for (String content : result.split("','")) {
                    if (content.startsWith("http")) {
                        logger.info("正在登录，请稍后");

                        return content;
                    }
                }
            } else if (result.contains("已失效")) {
                logger.info("二维码已失效,尝试重新获取二维码");
                getQRCode();
            }
        }

    }

    //登录流程3：获取ptwebqq
    private void getPtwebqq(String url) {
        logger.debug("开始获取ptwebqq");

        Response<String> response = get(ApiURL.GET_PTWEBQQ, url);
        this.ptwebqq = response.getCookies().get("ptwebqq").iterator().next().getValue();
    }

    //登录流程4：获取vfwebqq
    private void getVfwebqq() {
        logger.debug("开始获取vfwebqq");

        Response<String> response = get(ApiURL.GET_VFWEBQQ, ptwebqq);
        this.vfwebqq = getJsonObjectResult(response).getString("vfwebqq");
    }

    //登录流程5：获取uin和psessionid
    private void getUinAndPsessionid() {
        logger.debug("开始获取uin和psessionid");

        JSONObject r = new JSONObject();
        r.put("ptwebqq", ptwebqq);
        r.put("clientid", Client_ID);
        r.put("psessionid", "");
        r.put("status", "online");

        Response<String> response = post(ApiURL.GET_UIN_AND_PSESSIONID, r);
        JSONObject result = getJsonObjectResult(response);
        this.psessionid = result.getString("psessionid");
        this.uin = result.getLongValue("uin");
    }

    public List<FriendStatus> getFriendStatus() {
        logger.debug("开始获取好友状态");
        Response<String> response = get(ApiURL.GET_FRIEND_STATUS, vfwebqq, psessionid);
        return JSON.parseArray(getJsonArrayResult(response).toJSONString(), FriendStatus.class);
    }

    public UserInfo getAccountInfo() {
        logger.debug("开始获取登录用户信息");
        Response<String> response = get(ApiURL.GET_ACCOUNT_INFO);
        return JSON.parseObject(getJsonObjectResult(response).toJSONString(), UserInfo.class);
    }

    //发送get请求
    private Response<String> get(ApiURL url, Object... params) {
        HeadOnlyRequestBuilder request = session.get(url.buildUrl(params))
                .addHeader("User-Agent", ApiURL.USER_AGENT);
        if (url.getReferer() != null) {
            request.addHeader("Referer", url.getReferer());
        }
        return request.text();
    }

    //发送post请求
    private Response<String> post(ApiURL url, JSONObject r) {
        return session.post(url.getUrl())
                .addHeader("User-Agent", ApiURL.USER_AGENT)
                .addHeader("Referer", url.getReferer())
                .addHeader("Origin", url.getOrigin())
                .addForm("r", r.toJSONString())
                .text();
    }

    //获取返回json的result字段（JSONObject类型）
    private static JSONObject getJsonObjectResult(Response<String> response) {
        return getResponseJson(response).getJSONObject("result");
    }

    //检验Json返回结果
    private static JSONObject getResponseJson(Response<String> response) {
        if (response.getStatusCode() != 200) {
            throw new RequestException(String.format("请求失败,Http返回码[%d]", response.getStatusCode()));
        }
        JSONObject json = JSON.parseObject(response.getBody());
        Integer retCode = json.getInteger("retcode");
        if (retCode == null) {
            throw new RequestException("请求失败,Api返回异常");
        } else if (retCode != 0) {
            switch (retCode) {
                case 103: {
                    logger.error("请求失败,Api返回码[103]。你需要进入http://w.qq.com，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常");
                    break;
                }
                case 100100: {
                    logger.debug("请求失败,Api返回码[100100]");
                    break;
                }
                default: {
                    throw new RequestException(String.format("请求失败,Api返回码[%d]", retCode));
                }
            }
        }
        return json;
    }

    //获取返回json的result字段（JSONArray类型）
    private static JSONArray getJsonArrayResult(Response<String> response) {
        return getResponseJson(response).getJSONArray("result");
    }

    //线程暂停
    private static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            //忽略InterruptedException
        }
    }


    /**
     * 获得好友列表（包含分组信息）
     */
    public List<Category> getFriendListWithCategory() {
        logger.debug("开始获取好友列表");
        JSONObject body = new JSONObject();
        body.put("vfwebqq", vfwebqq);
        body.put("hash", hash());

        Response<String> response = post(ApiURL.GET_FRIEND_LIST, body);
        JSONObject result = getJsonObjectResult(response);
        //获得好友信息
        Map<Long, Friend> friendMap = parseFriendMap(result);
        //获得分组
        JSONArray categories = result.getJSONArray("categories");
        Map<Integer, Category> categoryMap = new HashMap<>();
        categoryMap.put(0, Category.defaultCategory());
        for (int i = 0; categories != null && i < categories.size(); i++) {
            Category category = categories.getObject(i, Category.class);
            categoryMap.put(category.getIndex(), category);
        }
        JSONArray friends = result.getJSONArray("friends");
        for (int i = 0; friends != null && i < friends.size(); i++) {
            JSONObject item = friends.getJSONObject(i);
            Friend friend = friendMap.get(item.getLongValue("uin"));
            categoryMap.get(item.getIntValue("categories")).addFriend(friend);
        }
        return new ArrayList<>(categoryMap.values());
    }

    //将json解析为好友列表
    private static Map<Long, Friend> parseFriendMap(JSONObject result) {
        Map<Long, Friend> friendMap = new HashMap<>();
        JSONArray info = result.getJSONArray("info");
        for (int i = 0; info != null && i < info.size(); i++) {
            JSONObject item = info.getJSONObject(i);
            Friend friend = new Friend();
            friend.setUserId(item.getLongValue("uin"));
            friend.setNickname(item.getString("nick"));
            friendMap.put(friend.getUserId(), friend);
        }
        JSONArray marknames = result.getJSONArray("marknames");
        for (int i = 0; marknames != null && i < marknames.size(); i++) {
            JSONObject item = marknames.getJSONObject(i);
            friendMap.get(item.getLongValue("uin")).setMarkname(item.getString("markname"));
        }
        JSONArray vipinfo = result.getJSONArray("vipinfo");
        for (int i = 0; vipinfo != null && i < vipinfo.size(); i++) {
            JSONObject item = vipinfo.getJSONObject(i);
            Friend friend = friendMap.get(item.getLongValue("u"));
            friend.setVip(item.getIntValue("is_vip") == 1);
            friend.setVipLevel(item.getIntValue("vip_level"));
        }
        return friendMap;
    }

    //hash加密方法
    private String hash() {
        return hash(uin, ptwebqq);
    }

    //hash加密方法
    private static String hash(long x, String K) {
        int[] N = new int[4];
        for (int T = 0; T < K.length(); T++) {
            N[T % 4] ^= K.charAt(T);
        }
        String[] U = {"EC", "OK"};
        long[] V = new long[4];
        V[0] = x >> 24 & 255 ^ U[0].charAt(0);
        V[1] = x >> 16 & 255 ^ U[0].charAt(1);
        V[2] = x >> 8 & 255 ^ U[1].charAt(0);
        V[3] = x & 255 ^ U[1].charAt(1);

        long[] U1 = new long[8];

        for (int T = 0; T < 8; T++) {
            U1[T] = T % 2 == 0 ? N[T >> 1] : V[T >> 1];
        }

        String[] N1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String V1 = "";
        for (long aU1 : U1) {
            V1 += N1[(int) ((aU1 >> 4) & 15)];
            V1 += N1[(int) (aU1 & 15)];
        }
        return V1;
    }

    /**
     * 发送消息
     */
    public boolean sendMessageToFriend(long friendId, String msg) {
        JSONObject body = new JSONObject();
        body.put("to", friendId);
        body.put("content", JSON.toJSONString(Arrays.asList(msg, Arrays.asList("font", Font.DEFAULT_FONT))));  //注意这里虽然格式是Json，但是实际是String
        body.put("face", 573);
        body.put("clientid", Client_ID);
        body.put("msg_id", MESSAGE_ID++);
        body.put("psessionid", psessionid);

        Response<String> response = postWithRetry(ApiURL.SEND_MESSAGE_TO_FRIEND, body);
        return checkSendMsgResult(response);
    }

    //发送post请求，失败时重试
    private Response<String> postWithRetry(ApiURL url, JSONObject r) {
        int times = 0;
        Response<String> response;
        do {
            response = post(url, r);
            times++;
        } while (times < RETRY_TIMES && response.getStatusCode() != 200);
        return response;
    }

    //检查消息是否发送成功
    private static boolean checkSendMsgResult(Response<String> response) {
        if (response.getStatusCode() != 200) {
            logger.error(String.format("发送失败,Http返回码[%d]", response.getStatusCode()));
        }
        JSONObject json = JSON.parseObject(response.getBody());
        Integer errCode = json.getInteger("errCode");
        Integer retcode = json.getInteger("retcode");
        if ((errCode != null && errCode == 0) || (retcode != null && retcode == 100100)) {
            return true;
        } else {
            logger.error(String.format("发送失败,Api返回码[%d]", retcode));
            return false;
        }
    }

    /**
     * 获得qq号
     */
    public long getQQById(long friendId) {
        logger.debug("开始获取QQ号");

        Response<String> response = get(ApiURL.GET_QQ_BY_ID, friendId, vfwebqq);
        return getJsonObjectResult(response).getLongValue("account");
    }

    @Override
    public void close() throws IOException {
        if (this.client != null) {
            this.client.close();
        }
    }
}
