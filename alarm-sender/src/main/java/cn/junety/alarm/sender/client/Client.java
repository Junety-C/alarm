package cn.junety.alarm.sender.client;

import cn.junety.alarm.base.entity.DeliveryStatus;
import cn.junety.alarm.base.redis.JedisFactory;
import cn.junety.alarm.sender.common.Configure;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by caijt on 2017/3/5.
 */
public abstract class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final int MAX_RETRY_TIMES = 3;

    private String name;
    private String queueName;
    protected String channel;

    protected abstract boolean send(String message);

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
                if (!send(message)) {
                    // 消费失败，进行重试
                    resend(message);
                }
            } catch (Exception e) {
                logger.error("consume queue {} error, caused by", queueName, e);
            }
        }
    }

    private void resend(String message) {
        boolean success = false;
        for(int i = 1; i <= MAX_RETRY_TIMES && !success; i++) {
            logger.info("send alarm fail, retry times:{}, data:{}", i, message);
            success = send(message);
        }
        logger.info("send alarm always fail, content:{}", message);
    }

    protected void markDeliveryStatus(long logId, String channel) {
        String data = JSON.toJSONString(new DeliveryStatus(logId, channel));
        try (Jedis jedis = JedisFactory.getJedisInstance(Configure.DELIVERY_QUEUE)) {
            jedis.rpush(Configure.DELIVERY_QUEUE, data);
            logger.debug("write redis delivery queue success, data:{}", data);
        } catch (Exception e) {
            logger.error("write redis delivery queue error, data:{}, caused by", data, e);
        }
    }
}
