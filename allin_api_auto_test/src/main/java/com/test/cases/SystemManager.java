package com.test.cases;

import com.alibaba.fastjson.JSONObject;
import com.test.utils.HttpUtils;
import org.databene.benerator.anno.Source;
import org.databene.feed4testng.FeedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.test.cases.Login.*;

public class SystemManager extends FeedTest {

    public Logger log = LoggerFactory.getLogger(SystemManager.class);

    @Test(dataProvider = "feeder")
	@Source("source/test.csv")
    public void user_search(String name,String test){
        String path_search = "/dpas/api/v1/user/search";
        String params = "{\"filter\":{\"name\":\""+name+"\",\"user.status\":\"\"},\"pageNo\":1,\"pageSize\":10,\"sort\":{\"field\":\"id\",\"direction\":\"DESC\"}}";
        String res = HttpUtils.postByJson("http://"+dg_ip+path_search,authorization,params);
        log.info("user_search::{}",res);
        System.out.println(res);
        Assert.assertEquals(JSONObject.parseObject(res).getString("status"),"success1");
    }

}
