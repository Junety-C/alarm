package cn.junety.alarm.server.common;

import cn.junety.alarm.base.redis.JedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


/**
 * Created by caijt on 2017/1/28.
 */
public class IdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    public static Long generate(String pool) {
        Jedis client = null;
        try {
            client = JedisFactory.getJedisInstance(pool);
            return client.incr(pool);
        } catch (Exception e) {
            logger.error("generate id fail, caused by ", e);
            return 0L;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
