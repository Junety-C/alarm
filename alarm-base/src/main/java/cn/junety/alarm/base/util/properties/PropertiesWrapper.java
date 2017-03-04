package cn.junety.alarm.base.util.properties;

import java.util.Properties;

public class PropertiesWrapper {
    private Properties prop;

    public PropertiesWrapper(Properties prop) {
        this.prop = prop;
    }

    public String get(String key) {
        return prop.getProperty(key);
    }

    public Integer getInt(String key) {
        return Integer.valueOf(prop.getProperty(key));
    }

    public Long getLong(String key) {
        return Long.valueOf(prop.getProperty(key));
    }

    public Double getDouble(String key) {
        return Double.valueOf(prop.getProperty(key));
    }

    public Boolean getBool(String key) {
        String value = prop.getProperty(key);
        return "TRUE".equals(value) || "true".equals(value) || "1".equals(value);
    }
}
