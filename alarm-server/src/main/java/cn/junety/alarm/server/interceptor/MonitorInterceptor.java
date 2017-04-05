package cn.junety.alarm.server.interceptor;

import cn.junety.alarm.base.redis.JedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caijt on 2017/4/2.
 *
 * 对未登陆的使用者进行拦截
 */
public class MonitorInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MonitorInterceptor.class);

    private static final String ALARM_TOTAL_REQUEST_QUANTITY = "alarm.request.quantity";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try (Jedis jedis = JedisFactory.getJedisInstance("monitor")) {
            jedis.incr(ALARM_TOTAL_REQUEST_QUANTITY);
        } catch (Exception e) {
            logger.error("do monitor interceptor error, caused by", e);
        }
        return true;
    }
}
