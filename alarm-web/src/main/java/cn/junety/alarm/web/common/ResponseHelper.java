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

    public static String buildResponse(int code, String reqId, Object content) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("content", content);
        logger.info("reqId:{}, response code:{}", reqId, code);
        return JSON.toJSONString(params);
    }

}
