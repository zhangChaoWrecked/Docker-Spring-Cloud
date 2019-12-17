package com.alpha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * config-server 配置中心配置文件使用示例
 */
@Component
@ConfigurationProperties(prefix = "service-demo")
public class Properties {

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
