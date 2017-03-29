package cn.junety.alarm.api;

import cn.junety.alarm.api.impl.HttpHelper;
import cn.junety.alarm.api.impl.Level;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by caijt on 2017/3/29.
 */
public class AlarmClient {

    private static final String ALARM_API = "http://localhost:8089/v1/alarm";

    private static boolean TEST_MODE = false;

    private AlarmClient() {}

    public static boolean debug(int code, String content) {
        return debug(code, null, content);
    }

    public static boolean debug(int code, String routeKey, String content) {
        return send(code, routeKey, content, Level.DEBUG);
    }

    public static boolean info(int code, String content) {
        return info(code, null, content);
    }

    public static boolean info(int code, String routeKey, String content) {
        return send(code, routeKey, content, Level.INFO);
    }

    public static boolean error(int code, String content) {
        return error(code, null, content);
    }

    public static boolean error(int code, String routeKey, String content) {
        return send(code, routeKey, content, Level.ERROR);
    }

    private static boolean send(int code, String routeKey, String content, Level level) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("routeKey", routeKey);
        body.put("content", content);
        body.put("level", level);
        body.put("isTest", TEST_MODE);
        return HttpHelper.sendPost(ALARM_API, JSON.toJSONString(body));
    }

    public static boolean isTest() {
        return TEST_MODE;
    }

    public static void setTest(boolean test) {
        AlarmClient.TEST_MODE = test;
    }
}
