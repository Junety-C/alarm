package cn.junety.alarm.server.vo;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by caijt on 2017/1/28.
 */
public class JsonConfig {

    private static final Logger logger = LoggerFactory.getLogger(JsonConfig.class);

    private String jsonString;
    private Map config;

    public JsonConfig(String jsonString) {
        this.jsonString = jsonString;
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfig(String key, T defaultValue) {
        try {
            if(config == null) {
                config = JSON.parseObject(jsonString, Map.class);
            }
            T value = (T) config.get(key);
            return value == null ? defaultValue : value;
        } catch (Exception e) {
            logger.error("parse object error, jsonString:{}", jsonString, e);
            return defaultValue;
        }

    }
}
