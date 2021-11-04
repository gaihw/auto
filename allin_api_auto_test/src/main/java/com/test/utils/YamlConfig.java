package com.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlConfig {

    public static Logger log = LoggerFactory.getLogger(YamlConfig.class);

    private static Map<String, Map<String, Object>> properties = new HashMap<>();

    /**
     * 单例
     */
    public static final YamlConfig instance = new YamlConfig();

    static {
        System.setProperty("fileName", "logs/info.log");
        Yaml yaml = new Yaml();
        String env = System.getProperty("env")==null?"test":System.getProperty("env");
        try (InputStream in = YamlConfig.class.getClassLoader().getResourceAsStream("env-"+env+".yaml");) {
            properties = yaml.loadAs(in, HashMap.class);
        } catch (Exception e) {
            log.error("Init yaml failed !", e);
        }
    }


    /**
     * get yaml property
     *
     * @param key
     * @return
     */
    public Object getValueByKey(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return properties.get(key);
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : (Object) finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }

    public static void main(String[] args) {
        Object serverHost = YamlConfig.instance.getValueByKey("email.sender");
        System.out.println(serverHost);
    }
}
