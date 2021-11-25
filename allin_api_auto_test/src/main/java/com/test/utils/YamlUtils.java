package com.test.utils;

import com.alibaba.fastjson.JSONObject;
import com.test.entity.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlUtils {
    public static Logger log = LoggerFactory.getLogger(YamlUtils.class);

    private Map<String, Map<String, Object>> properties = new HashMap<>();
    public  String env = System.getProperty("env")==null?"test":System.getProperty("env");
    public String file_path = null;
    public  Yaml yaml = new Yaml();


    public YamlUtils(){
        file_path = "env-"+env+".yaml";
        try (InputStream in = YamlUtils.class.getClassLoader().getResourceAsStream(file_path);) {
            properties = yaml.loadAs(in, HashMap.class);
        } catch (Exception e) {
            log.error("Init yaml failed !", e);
        }
    }

    public YamlUtils(String relation){
        file_path = "src\\main\\resources\\data\\"+relation+".yaml";
        try (InputStream in = YamlUtils.class.getClassLoader().getResourceAsStream(file_path);) {
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

    public void setValue(String data){
        JSONObject m = new JSONObject();
        m.put("a","c");
        Data d = new Data();
        d.setKey("a");
        d.setValue(111);
        FileWriter file = null;
        try {
            file = new FileWriter(new File(file_path),true);
            yaml.dump(m,file);
            System.out.println("test...");
        }catch (Exception e){
            System.out.println("yaml文件未找到!");
            log.error("yaml文件未找到!");
        }
    }
    public static void main(String[] args) {
        YamlUtils yamlUtils = new YamlUtils("relation");
        yamlUtils.setValue("afda");
    }
}
