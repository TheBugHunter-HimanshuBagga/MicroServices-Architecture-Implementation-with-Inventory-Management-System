package com.HimanshuBagga.ecommerce.api_gateway.Filters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.servlet.filter.OrderedFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange , GateWayFilterChain chain){ // since it don't return anything
        // pre filter
        log.info("Logging from global Pre: {}" , exchange.getRequest().getURI());
        // post filter on then
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Logging from Global Post: {}", exchange.getResponse().getStatusCode() );
        }));
    }

    @Override
    public int getOrder() {
        return 5; // this filter will come at order 5 or it will run at 5
    }
}
