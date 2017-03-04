package cn.junety.alarm.server.channel;

import cn.junety.alarm.base.redis.JedisFactory;
import cn.junety.alarm.server.vo.AlarmMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by caijt on 2017/1/28.
 */
public abstract class Channel {

    private static Logger logger = LoggerFactory.getLogger(Channel.class);

    private static final int MAX_RETRY_TIMES = 3;

    public abstract void send(AlarmMessage alarmMessage);
    protected abstract String getPreSendingQueue();

    void save(String message) {
        boolean success = false;
        for(int i = 0; i < MAX_RETRY_TIMES && !success; i++) {
            success = saveToPreSendQueue(this.getPreSendingQueue(), message, i);
        }
    }

    private boolean saveToPreSendQueue(String queue, String message, int times) {
        Jedis client = null;
        try {
            client = JedisFactory.getJedisInstance(queue);
            client.rpush(queue, message);
            logger.debug("write redis queue-{} success, retry times:{}, value:{}", queue, times, message);
            return true;
        } catch (Exception e) {
            logger.error("write redis queue-{} fail, retry times:{}, value:{}", queue, times, message, e);
            return false;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

}
