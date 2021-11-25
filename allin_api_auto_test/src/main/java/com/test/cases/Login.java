package com.test.cases;

import com.alibaba.fastjson.JSONObject;
import com.test.utils.CommUtils;
import com.test.utils.HttpUtils;
import com.test.utils.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;

public class Login {

    public Logger log = LoggerFactory.getLogger(Login.class);

    public static YamlUtils instance = new YamlUtils();
    public static String dg_ip = String.valueOf(instance.getValueByKey("test.ip.dg_ip"));

    public static String uuid = "";
    public static String token = "";
    public static HashMap<String,String> authorization = new HashMap<String,String>();

    @BeforeSuite
    public void login(){

        try {
            //获取captcha
            JSONObject captcha_res = JSONObject.parseObject(HttpUtils.postByJson("http://"+dg_ip+String.valueOf(instance.getValueByKey("test.path.captcha"))));
            String captcha = captcha_res.getJSONObject("data").getString("captcha");
            String key = captcha_res.getJSONObject("data").getString("key");

            //登录
            String login_param = "{\"captcha\": \""+captcha+"\",\"key\": \""+key+"\",\"password\": \""+ CommUtils.stringToMD5(String.valueOf(instance.getValueByKey("test.acount.phone_password")))+"\",\"username\": \""+String.valueOf(instance.getValueByKey("test.acount.phone_number"))+"\"}";
            JSONObject login_res = JSONObject.parseObject(HttpUtils.postByJson("http://"+dg_ip+String.valueOf(instance.getValueByKey("test.path.login")),login_param));

            log.info("登录数据:{}",login_res);

            uuid = login_res.getJSONObject("data").getString("uuid");
            token = login_res.getJSONObject("data").getString("token");
            authorization.put("token",token);
            authorization.put("uid",uuid);
//            authorization.put("appId",String.valueOf(YamlUtils.instance.getValueByKey("test.appId")));
            log.info("header:{}",authorization);

        }catch (Exception e){
            log.error("登录失败,信息为:{}",e.toString());
        }

    }
}
