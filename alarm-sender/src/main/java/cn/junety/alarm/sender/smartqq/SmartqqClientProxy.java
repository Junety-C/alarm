package cn.junety.alarm.sender.smartqq;

import cn.junety.alarm.sender.smartqq.entity.Category;
import cn.junety.alarm.sender.smartqq.entity.Friend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by caijt on 2017/3/28.
 */
public class SmartqqClientProxy {

    private static final Logger logger = LoggerFactory.getLogger(SmartqqClientProxy.class);

    // 默认5分钟刷新一次好友列表
    private static final long DEFAULT_TIME_INTERVAL = 5 * 60;

    private ScheduledExecutorService scheduledExecutorService;
    private Map<String, Long> userIdMapper;
    private SmartqqClient client;

    public SmartqqClientProxy() {
        client = new SmartqqClient();
        this.scheduledExecutorService =  Executors.newSingleThreadScheduledExecutor();
        refreshFriendList();
        scheduledExecutorService.scheduleAtFixedRate( () -> new Thread(this::refreshFriendList).start(),
                DEFAULT_TIME_INTERVAL, DEFAULT_TIME_INTERVAL, TimeUnit.SECONDS);
    }

    private void refreshFriendList() {
        List<Category> categories = client.getFriendListWithCategory();
        Map<String, Long> qqToUserId = new HashMap<>();
        for (Category category : categories) {
            for (Friend friend : category.getFriends()) {
                Long qq = client.getQQById(friend.getUserId());
                qqToUserId.put(""+qq, friend.getUserId());
            }
        }
        userIdMapper = qqToUserId;
        logger.debug("qq friend list refresh success, size:{}", userIdMapper.size());
    }

    public void send(List<String> qqList, String content) {
        for(String qq : qqList) {
            try {
                Long userId = userIdMapper.get(qq);
                if (userId != null) {
                    client.sendMessageToFriend(userId, content);
                } else {
                    logger.debug("send message fail, userId not found, qq:{}, content:{}", qq, content);
                }
            } catch (Exception e) {
                logger.error("send message error, qq:{}, content:{}, caused by", e);
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
}
