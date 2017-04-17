package cn.junety.alarm.server.common;

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

    public static String buildResponse(int code, Object... vars) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        for(int i = 0; i < vars.length; i+=2) {
            params.put(vars[i].toString(), vars[i+1]);
        }
        String body = JSON.toJSONString(params);
        logger.info("response body:{}", body);
        return body;
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
