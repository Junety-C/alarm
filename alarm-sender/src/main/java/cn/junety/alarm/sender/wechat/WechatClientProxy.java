package cn.junety.alarm.sender.wechat;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by caijt on 2017/3/29.
 */
public class WechatClientProxy {

    private static final Logger logger = LoggerFactory.getLogger(WechatClientProxy.class);

    // 默认5分钟刷新一次好友列表
    private static final long DEFAULT_TIME_INTERVAL = 5 * 60;

    private ScheduledExecutorService scheduledExecutorService;
    private Map<String, String> aliasMapper;
    private WechatClient client;

    public WechatClientProxy() {
        client = new WechatClient();
        this.scheduledExecutorService =  Executors.newSingleThreadScheduledExecutor();
        while(aliasMapper == null) {
            refreshFriendList();
        }
        scheduledExecutorService.scheduleAtFixedRate( () -> new Thread(this::refreshFriendList).start(),
                DEFAULT_TIME_INTERVAL, DEFAULT_TIME_INTERVAL, TimeUnit.SECONDS);
    }

    private void refreshFriendList() {
        try {
            List<JSONObject> friends = client.getFriendList();
            Map<String, String> aliasToUserName = new HashMap<>();
            for(JSONObject friend : friends) {
                String alias = friend.getString("Alias");
                String userName = friend.getString("UserName");
                aliasToUserName.put(alias, userName);
            }
            aliasMapper = aliasToUserName;
            logger.debug("wechat friend list refresh success, size:{}", aliasMapper.size());
        } catch (Exception e) {
            logger.error("refresh friend list error, caused by", e);
        }
    }

    public void send(List<String> wechatList, String content) {
        for(String alias : wechatList) {
            String userName = aliasMapper.get(alias);
            if (userName != null) {
                client.sendMessageToFriend(userName, content);
            } else {
                logger.debug("send message fail, userName not found, alias:{}, content:{}", alias, content);
            }
        }
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WechatClientProxy client = new WechatClientProxy();
        client.send(Arrays.asList("your wechat number"), "告警测试");
    }
}
