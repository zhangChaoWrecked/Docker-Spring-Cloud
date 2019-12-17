package com;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;


/**
 * Spring Boot Admin Client端示列
 */


@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
@Component
public class MonitoringServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitoringServerApplication.class, args);
    }


    @Bean
    public MessageNotifier dingDingNotifier(InstanceRepository repository) {
        return new MessageNotifier(repository);
    }

    @Primary
    @Bean(initMethod = "start", destroyMethod = "stop")
    public RemindingNotifier remindingNotifier(InstanceRepository repository) {
        RemindingNotifier notifier = new RemindingNotifier(dingDingNotifier(repository), repository);
        notifier.setReminderPeriod(Duration.ofSeconds(10));
        notifier.setCheckReminderInverval(Duration.ofSeconds(10));
        return notifier;
    }
}