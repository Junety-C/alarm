package cn.junety.alarm.base.redis;

import cn.junety.alarm.base.util.properties.PropertiesLoader;
import cn.junety.alarm.base.util.properties.PropertiesWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caijt on 2017/1/28.
 */
public class JedisFactory {
    private static final Logger logger = LoggerFactory.getLogger(JedisFactory.class);

    private static Map<String, JedisPool> jedisPoolMap = new HashMap<>();
    private static final int DEFAULT_POOL_SIZE = 100;
    private static final int DEFAULT_POLL_IDLE = 10;
    private static final int DEFAULT_TIMEOUT = 1000;

    public static Jedis getJedisInstance() {
        return getJedisInstance("default");
    }

    public static Jedis getJedisInstance(String jedisName) {
        try {
            JedisPool jedisPool = initJedisPool(jedisName);
            Jedis jedis = null;
            for (int i = 1; i <= 3; i++) {
                try {
                    jedis = jedisPool.getResource();
                    break;
                } catch (Exception e) {
                    logger.error("get resource from pool fail, times:{}, error:{}", i, e.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        logger.error("sleep error", e1);
                    }
                }
            }

            if (jedis == null) {
                throw new RuntimeException(String.format("get resource from pool-%s fail,", jedisName));
            }
            return jedis;
        } catch (Exception e) {
            throw new RuntimeException(String.format("init redis-%s fail", jedisName), e);
        }

    }

    private static synchronized JedisPool initJedisPool(String jedisName) {
        JedisPool jPool = jedisPoolMap.get(jedisName);
        if (jPool == null) {
            PropertiesWrapper prop = PropertiesLoader.load("redis.properties");
            String host = prop.get(jedisName + ".redis.host");
            int port = prop.getInt(jedisName + ".redis.port");
            jPool = createJedisPool(host, port);
            jedisPoolMap.put(jedisName, jPool);
        }
        return jPool;
    }

    private static JedisPool createJedisPool(String host, int port) {
        logger.info("init redis poll {}:{}", host, port);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setMaxTotal(DEFAULT_POOL_SIZE);
        poolConfig.setMaxIdle(DEFAULT_POOL_SIZE);
        poolConfig.setMinIdle(DEFAULT_POLL_IDLE);
        return new JedisPool(poolConfig, host, port, DEFAULT_TIMEOUT);
    }
}
