package cn.junety.alarm.web.common;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caijt on 2017/1/28.
 */
public class ResponseHelper {

    private static Logger logger = LoggerFactory.getLogger(ResponseHelper.class);

    public static String buildResponse(int code, Object... vars) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        for(int i = 0; i < vars.length; i+=2) {
            params.put(vars[i].toString(), vars[i+1]);
        }
        logger.info("response code:{}", code);
        return JSON.toJSONString(params);
    }

}
