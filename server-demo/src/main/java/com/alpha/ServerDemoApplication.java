package com.alpha;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;


/**
 * Server-Demo  示例微服务
 */
@EnableDiscoveryClient  // Eureka客户端
@SpringBootApplication
@Component
@EnableHystrix
public class ServerDemoApplication   {
    public static void main(String[] args) {
        SpringApplication.run(ServerDemoApplication.class, args);
    }
}