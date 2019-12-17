package com.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 自定义全局过滤器   实现 GlobalFilter, Ordered
 * <p>
 * getOrder() 返回的值越小 优先级越高
 */
@Component
public class CheckFilter implements GlobalFilter, Ordered {
    private Logger logger = LoggerFactory.getLogger(CheckFilter.class);

    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("过滤器CheckFilter-------");
        return chain.filter(exchange);

    }


}
