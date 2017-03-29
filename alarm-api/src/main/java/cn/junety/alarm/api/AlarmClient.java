package cn.junety.alarm.api;

import cn.junety.alarm.api.impl.AlarmSender;
import cn.junety.alarm.api.impl.Level;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * Created by caijt on 2017/3/29.
 */
public class AlarmClient {

    private static boolean TEST_MODE = false;
    private static int QUEUE_SIZE = 100;

    private static AlarmSender alarmSender;

    private AlarmClient() {}

    public static void debug(int code, String content) {
        debug(code, null, content);
    }

    public static void debug(int code, String routeKey, String content) {
        send(code, routeKey, content, Level.DEBUG);
    }

    public static void info(int code, String content) {
        info(code, null, content);
    }

    public static void info(int code, String routeKey, String content) {
        send(code, routeKey, content, Level.INFO);
    }

    public static void error(int code, String content) {
        error(code, null, content);
    }

    public static void error(int code, String routeKey, String content) {
        send(code, routeKey, content, Level.ERROR);
    }

    private static void send(int code, String routeKey, String content, Level level) {
        if(alarmSender == null) {
            createSender();
        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("routeKey", routeKey);
        body.put("content", content);
        body.put("level", level);
        body.put("isTest", TEST_MODE);
        alarmSender.send(JSON.toJSONString(body));
    }

    private static synchronized void createSender() {
        if(alarmSender == null) {
            alarmSender = new AlarmSender(QUEUE_SIZE);
        }
    }

    public static int getQueueSize() {
        return QUEUE_SIZE;
    }

    public static void setQueueSize(int queueSize) {
        QUEUE_SIZE = queueSize;
    }

    public static boolean isTest() {
        return TEST_MODE;
    }

    public static void setTest(boolean test) {
        AlarmClient.TEST_MODE = test;
    }
}
