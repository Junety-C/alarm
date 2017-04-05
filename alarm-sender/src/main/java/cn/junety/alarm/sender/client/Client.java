package cn.junety.alarm.sender.client;

import cn.junety.alarm.base.entity.DeliveryStatus;
import cn.junety.alarm.base.redis.JedisFactory;
import cn.junety.alarm.base.util.DateUtil;
import cn.junety.alarm.sender.configuration.Configuration;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Created by caijt on 2017/3/5.
 */
public abstract class Client {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int MAX_RETRY_TIMES = 3;

    private String name;
    private String queueName;
    protected String channel;

    protected abstract boolean send(String message);
    protected abstract String getPushQuantityKey();
    protected abstract String getPushDailyKey();

    public Client(String name, String queueName, String channel) {
        this.name = name;
        this.queueName = queueName;
        this.channel = channel;
    }

    public void start() {
        logger.info("start {} client success", name);
        while (true) {
            try (Jedis jedis = JedisFactory.getJedisInstance(queueName)) {
                String message = jedis.blpop(0, queueName).get(1);
                if (sendWithRetry(message)) {
                    recordPushNumber();
                }
            } catch (Exception e) {
                logger.error("consume queue {} error, caused by", queueName, e);
            }
        }
    }

    /**
     * 各个渠道的推送数量统计(每日和总量)
     */
    private void recordPushNumber() {
        try (Jedis jedis = JedisFactory.getJedisInstance("monitor")) {
            String pushQuantityKey = getPushQuantityKey();
            if (pushQuantityKey != null) {
                jedis.incr(pushQuantityKey);
            }
            String pushDailyKey = getPushDailyKey();
            if (pushDailyKey != null) {
                String date = DateUtil.formatDate(DateUtil.YYYYMMDD, new Date());
                jedis.incr(pushDailyKey.replace("{date}", date));
            }
        } catch (Exception e) {
            logger.error("record push number error, key:{}, caused by", getPushQuantityKey(), e);
        }
    }

    private boolean sendWithRetry(String message) {
        boolean success = false;
        for(int i = 1; i <= MAX_RETRY_TIMES && !success; i++) {
            logger.info("send alarm fail, retry times:{}, data:{}", i, message);
            success = send(message);
        }
        if (success) {
            return true;
        }
        logger.info("send alarm always fail, content:{}", message);
        return false;
    }

    protected void markDeliveryStatus(long logId, String channel) {
        String data = JSON.toJSONString(new DeliveryStatus(logId, channel));
        try (Jedis jedis = JedisFactory.getJedisInstance(Configuration.DELIVERY_QUEUE)) {
            jedis.rpush(Configuration.DELIVERY_QUEUE, data);
            logger.debug("write redis delivery queue success, data:{}", data);
        } catch (Exception e) {
            logger.error("write redis delivery queue error, data:{}, caused by", data, e);
        }
    }
}
