package com.test.utils.testng;

import com.test.utils.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class OverrideIReTry implements IRetryAnalyzer {
    public YamlUtils instance = new YamlUtils();
    public Logger log = LoggerFactory.getLogger(OverrideIReTry.class);

    public int initReTryNum=1;
    public int maxReTryNum= (int)instance.getValueByKey("testng.max_retry_num");

    @Override
    public boolean retry(ITestResult iTestResult) {
        if(initReTryNum<=maxReTryNum){
            String message="方法<"+iTestResult.getName()+">执行失败，重试第"+initReTryNum+"次";
            log.info(message);
            Reporter.setCurrentTestResult(iTestResult);
            Reporter.log(message);
            initReTryNum++;
            return true;
        }
        return false;
    }
}
