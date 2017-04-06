package cn.junety.alarm.web.service;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.base.redis.JedisFactory;
import cn.junety.alarm.base.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caijt on 2017/4/5.
 */
@Service
public class MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);

    /**
     * 获取发送队列状态
     * @return 各个队列的大小
     */
    public Map<String, Long> getQueueStatus() {
        try(Jedis jedis = JedisFactory.getJedisInstance("monitor")) {
            Map<String, Long> queueStatus = new HashMap<>();
            queueStatus.put("mail", jedis.llen(ConfigKey.MAIL_QUEUE.value()));
            queueStatus.put("sms", jedis.llen(ConfigKey.SMS_QUEUE.value()));
            queueStatus.put("qq", jedis.llen(ConfigKey.QQ_QUEUE.value()));
            queueStatus.put("wechat", jedis.llen(ConfigKey.WECHAT_QUEUE.value()));
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
            String quantity = jedis.get(ConfigKey.TOTAL_REQUEST_QUANTITY.value());
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

    /**
     * 获取各个发送端的发送量
     */
    public Map<String, String> getPushQuantity() {
        Map<String, String> sendingQuantity = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        String today = DateUtil.formatDate(DateUtil.YYYYMMDD, calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String yesterday = DateUtil.formatDate(DateUtil.YYYYMMDD, calendar.getTime());

        try(Jedis jedis = JedisFactory.getJedisInstance("monitor")) {
            sendingQuantity.put("mail_today", jedis.get(ConfigKey.MAIL_PUSH_DAILY.value().replace("{date}", today)));
            sendingQuantity.put("sms_today", jedis.get(ConfigKey.SMS_PUSH_DAILY.value().replace("{date}", today)));
            sendingQuantity.put("qq_today", jedis.get(ConfigKey.QQ_PUSH_DAILY.value().replace("{date}", today)));
            sendingQuantity.put("wechat_today", jedis.get(ConfigKey.WECHAT_PUSH_DAILY.value().replace("{date}", today)));

            sendingQuantity.put("mail_yesterday", jedis.get(ConfigKey.MAIL_PUSH_DAILY.value().replace("{date}", yesterday)));
            sendingQuantity.put("sms_yesterday", jedis.get(ConfigKey.SMS_PUSH_DAILY.value().replace("{date}", yesterday)));
            sendingQuantity.put("qq_yesterday", jedis.get(ConfigKey.QQ_PUSH_DAILY.value().replace("{date}", yesterday)));
            sendingQuantity.put("wechat_yesterday", jedis.get(ConfigKey.WECHAT_PUSH_DAILY.value().replace("{date}", yesterday)));

            sendingQuantity.put("mail_total", jedis.get(ConfigKey.MAIL_PUSH_QUANTITY.value()));
            sendingQuantity.put("sms_total", jedis.get(ConfigKey.SMS_PUSH_QUANTITY.value()));
            sendingQuantity.put("qq_total", jedis.get(ConfigKey.QQ_PUSH_QUANTITY.value()));
            sendingQuantity.put("wechat_total", jedis.get(ConfigKey.WECHAT_PUSH_QUANTITY.value()));

            return sendingQuantity;
        } catch (Exception e) {
            logger.error("get system throughput error, caused by", e);
            return Collections.emptyMap();
        }
    }
}
