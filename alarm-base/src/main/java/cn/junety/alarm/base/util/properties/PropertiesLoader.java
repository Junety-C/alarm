package cn.junety.alarm.base.util.properties;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {
    private static Map<String, PropertiesWrapper> propertiesWrapperMap = new HashMap<>();

    public static void init(Class cls) {
        // 读取配置文件
        Config config = (Config) cls.getAnnotation(Config.class);
        if (config == null || StringUtils.isEmpty(config.value())) {
            throw new RuntimeException(String.format("Can not find %s's properties", cls.getSimpleName()));
        }
        Properties prop = new Properties();
        try {
            prop.load(PropertiesLoader.class.getClassLoader().getResourceAsStream(config.value()));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can not load %s's properties", cls.getSimpleName()));
        }

        // 获取需要注入配置的字段
        Map<Field, Key> keyFields = new HashMap<Field, Key>();
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            Key key = field.getAnnotation(Key.class);
            if (key != null) {
                keyFields.put(field, field.getAnnotation(Key.class));
            }
        }

        // 注入配置

        for(Field field : keyFields.keySet()) {
            Key key = keyFields.get(field);
            Class type = field.getType();
            String value = prop.getProperty(key.value());
            try {
                if (type == String.class) {
                    field.set(null, value);
                } else if (type == Integer.class) {
                    field.set(null, Integer.valueOf(value));
                } else if (type == Boolean.class) {
                    field.set(null, "TRUE".equals(value) || "true".equals(value) || "1".equals(value));
                } else if (type == Double.class) {
                    field.set(null, Double.valueOf(value));
                } else if (type == Float.class) {
                    field.set(null, Float.valueOf(value));
                } else if (type == Long.class) {
                    field.set(null, Long.valueOf(value));
                } else {
                    throw new RuntimeException(String.format("Un-support load %s properties", type.getSimpleName()));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Can not access field, " + e.getMessage());
            }

        }
    }

    public static synchronized PropertiesWrapper load(String filename) {
        if (StringUtils.isEmpty(filename)) {
            throw new IllegalArgumentException("Properties filename can't not be null");
        }

        if (propertiesWrapperMap.containsKey(filename)) {
            return propertiesWrapperMap.get(filename);
        }

        Properties prop = new Properties();
        try {
            prop.load(PropertiesLoader.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can not load properties from %s", filename));
        }

        PropertiesWrapper wrapper = new PropertiesWrapper(prop);
        propertiesWrapperMap.put(filename, wrapper);
        return wrapper;
    }


    private PropertiesLoader() {}
}
