package cn.junety.alarm.web.service;

import cn.junety.alarm.base.redis.JedisFactory;
import cn.junety.alarm.web.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caijt on 2017/4/5.
 */
@Service
public class MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);

    private static final String ALARM_TOTAL_REQUEST_QUANTITY = "alarm.request.quantity";

    /**
     * 获取发送队列状态
     * @return 各个队列的大小
     */
    public Map<String, Long> getQueueStatus() {
        try(Jedis jedis = JedisFactory.getJedisInstance("monitor")) {
            Map<String, Long> queueStatus = new HashMap<>();
            queueStatus.put("mail", jedis.llen(Configuration.MAIL_REDIS_QUEUE));
            queueStatus.put("sms", jedis.llen(Configuration.SMS_REDIS_QUEUE));
            queueStatus.put("qq", jedis.llen(Configuration.QQ_REDIS_QUEUE));
            queueStatus.put("wechat", jedis.llen(Configuration.WECHAT_REDIS_QUEUE));
            return queueStatus;
        } catch (Exception e) {
            logger.error("get queue status error, caused by", e);
            return Collections.emptyMap();
        }
    }

    /**
     * 获取当前系统处理请求的总量
     * @return 请求总量
     */
    public int getTotalRequestQuantity() {
        try(Jedis jedis = JedisFactory.getJedisInstance("monitor")) {
            String quantity = jedis.get(ALARM_TOTAL_REQUEST_QUANTITY);
            if (quantity == null) {
                return 0;
            } else {
                return Integer.parseInt(quantity);
            }
        } catch (Exception e) {
            logger.error("get system throughput error, caused by", e);
            return 0;
        }
    }
}
