package com.HimanshuBagga.ecommerce.api_gateway.Filters;

import com.netflix.spectator.impl.Config;
import org.springframework.cloud.gateway.server.mvc.common.AbstractGatewayDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingOrdersFilter extends AbstractGatewayDiscoverer<LoggingOrdersFilter.Config> {

    public static class config{

    }

    @Override
    public GatewayFilter apply(Config config) {
//        return new GateWayFilter(){
//            @Override
//            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
//                return null;
//            }
//        };
        return(exchange , chain) -> {
            log.info("Order Filter pre : {}" , exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }

    public LoggingOrdersFilter(Class<config> configClass) {
        super(configClass);
    }
}
