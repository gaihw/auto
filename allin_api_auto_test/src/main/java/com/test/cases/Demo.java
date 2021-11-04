package com.test.cases;

import com.test.utils.YamlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class Demo {

    static {
        System.setProperty("fileName", "contract/info.log");
    }
    public static Logger log = LoggerFactory.getLogger(Demo.class);

    @BeforeClass
    public void b(){
        System.out.println("b ...");
        log.info("b ....");
    }
    @Test
    public void one(){
        System.out.println("one ...");
        log.info("one ....");
    }
    @Test
    public void three(){
        Assert.assertEquals(1,2);
    }



    @Test
    public void two(){
        System.out.println("two ...");
    }

    @Test
    public void four() throws FileNotFoundException {

//        System.out.println(java.lang.System.getProperties());
        System.out.println("======================env======================");
        String env = System.getProperty("env")==null?"test":System.getProperty("env");
        System.out.println(env);
        System.out.println("============================================");
//        EnvEntity envEntity = (EnvEntity) Yaml.loadType(new FileInputStream(new File("src\\main\\resources\\env-"+env+".yaml")),EnvEntity.class);
//        System.out.println(envEntity);
        System.out.println("============================================");

        Object serverHost = YamlConfig.instance.getValueByKey("a.b.c");
        System.out.println(serverHost);
        System.out.println("============================================");

    }

}
