package com.alpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * EurekaServer
 *
 * @date 2019-11-20
 */
@EnableEurekaServer  //开启 Eureka Server
@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }



    /**
     * 开启注册中心安全认证
     */
    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //关闭csrf
            http.csrf().disable();
            // 支持httpBasic
            http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        }
    }

}
