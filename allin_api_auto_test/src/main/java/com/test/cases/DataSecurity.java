package com.test.cases;

import com.alibaba.fastjson.JSONObject;
import com.test.utils.CommUtils;
import com.test.utils.HttpUtils;
import org.databene.benerator.anno.Source;
import org.databene.feed4testng.FeedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.test.cases.Login.*;

public class DataSecurity extends FeedTest {

    public Logger log = LoggerFactory.getLogger(DataSecurity.class);


    @Test
    public void mask_rule_search() {
        String path_search = "/data-governance/api/v1/data_security/mask_rule/search";
        String params = "{\"pageNo\":1,\"pageSize\":10,\"filter\":{\"name\":\"\"},\"sort\":{\"field\":\"createdDate\",\"direction\":\"DESC\"}}";
        String res = HttpUtils.postByJson("http://"+dg_ip+path_search,authorization,params);
        log.info("mask_rule_search::{}",res);
        System.out.println(res);
        Assert.assertEquals(JSONObject.parseObject(res).getString("status"),"success");

    }

    @Test
    public void data_assets_level_all(){
        String path_all = "/data-governance/api/v1/data_assets/tag/DATA_ASSETS_LEVEL/all";
        String res = HttpUtils.get("http://"+dg_ip+path_all,authorization);
        log.info("data_assets_level_all::{}",res);
        Assert.assertEquals(JSONObject.parseObject(res).getString("status"),"success");
    }

    @Test(dataProvider = "feeder")
    @Source("source/dataSecurity/mask_rule.csv")
    public void mask_rule(String rule_name){
        String path_mask_rule = "/data-governance/api/v1/data_security/mask_rule";
        String params = "{\"name\":\"脱敏规则"+ rule_name+"\",\"dataTypeId\":6,\"levelId\":10000296,\"maskMode\":\"VAGUE_DATA\",\"maskForm\":{\"fromPosition\":1,\"toPosition\":3,\"replaceCharacter\":\"***\"},\"replaceCharacter\":\"***\",\"opened\":false}";
//        String params = "{\"name\":\"脱敏规则"+ CommUtils.time()+"\",\"dataTypeId\":6,\"levelId\":10000296,\"maskMode\":\"VAGUE_DATA\",\"maskForm\":{\"fromPosition\":1,\"toPosition\":3,\"replaceCharacter\":\"***\"},\"replaceCharacter\":\"***\",\"opened\":false}";
        String res = HttpUtils.postByJson("http://"+dg_ip+path_mask_rule,authorization,params);
        log.info("mask_rule::{}",res);
        Assert.assertEquals(JSONObject.parseObject(res).getString("status"),"success");
    }
}
