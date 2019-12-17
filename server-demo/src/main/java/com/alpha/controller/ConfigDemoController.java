package com.alpha.controller;

import com.alpha.config.Properties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
/**
 * @RefreshScope 注明
 */
@RefreshScope
public class ConfigDemoController {
    private Logger logger = LoggerFactory.getLogger(ConfigDemoController.class);

    @Autowired
    private Properties properties;

    /**
     * 测试Config Server 更改的配置
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "sayHelloDefault")
    @GetMapping(value = "/autoShow")
    public Object autoShow() throws InterruptedException {
        logger.info("获取Config Server 配置信息" + System.currentTimeMillis());
//        Thread.currentThread().sleep(3000);
        return properties.getDesc();
    }

    public Object sayHelloDefault() {
        logger.info("/config/autoShow 调用失败进行熔断回退" + System.currentTimeMillis());
        return "配置信息获取失败进行熔断操作";
    }
}
