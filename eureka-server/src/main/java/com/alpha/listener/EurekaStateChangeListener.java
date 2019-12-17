package com.alpha.listener;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 服务上下限的监控
 */
@Component
public class EurekaStateChangeListener {
    private Logger logger = LoggerFactory.getLogger(EurekaStateChangeListener.class);

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        logger.error("[" + event.getServerId() + " ]服务下线");
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        logger.info("[ " + instanceInfo.getInstanceId() + " ]   进行注册");
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        logger.info("[" + event.getServerId() + " ]   服务进行续约");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        logger.info("注册中心 启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        logger.info("Eureka Server 启动");
    }

}
