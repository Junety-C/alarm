package cn.junety.alarm.web.common;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caijt on 2017/1/28.
 */
public class ResponseHelper {

    private static Logger logger = LoggerFactory.getLogger(ResponseHelper.class);

    private static final String[] PROXY_REMOTE_IP_ADDRESS = { "X-Forwarded-For", "X-Real-IP" };

    public static String buildResponse(int code, String content) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("content", content);
        String body = JSON.toJSONString(params);
        logger.info("Response: {}", body);
        return JSON.toJSONString(params);
    }

    public static String getRemoteIp(HttpServletRequest request ) {
        for (String key : PROXY_REMOTE_IP_ADDRESS) {
            String ip = request.getHeader(key);
            if (ip != null && ip.trim().length() > 0) {
                return getRemoteIpFromForward(ip.trim());
            }
        }
        return request.getRemoteHost();
    }

    private static String getRemoteIpFromForward(String xForwardIp) {
        int commaOffset = xForwardIp.indexOf(',');
        if (commaOffset < 0) {
            return xForwardIp;
        }
        return xForwardIp.substring(0 , commaOffset);
    }

}
