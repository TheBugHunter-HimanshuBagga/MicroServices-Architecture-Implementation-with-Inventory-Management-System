package com.HimanshuBagga.ecommerce.api_gateway.Filters;

import com.HimanshuBagga.ecommerce.api_gateway.Service.JwtService;
import com.netflix.spectator.impl.Config;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService){
        super(Config.class);
        this.jwtService = jwtService;
    }


    @Override
    public GatewayFilter apply(config config) {

        return (exchange , chain) -> {
            if(!config.isEnabled) return chain.filter(exchange);

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization"); // since we get the list<String> ["Bearer token1", "Bearer token2"] so using getFirst() we get "Bearer token1"

            if(authorizationHeader == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete(); // returned back from here only
            }
            String token = authorizationHeader.split("Bearer ")[1]; // we get the token

            // verify
            Long userId = jwtService.getUserIdFromToken(token);

            // this is authenticated now
            // now we need to mutation this since my downrequest doesn't know about userId

            exchange.getRequest()
                    .mutate()
                    .header("X-User-Id" , userId.toString())
                    .build();

            return chain.filter(exchange); // now userId  will pass in downstream service
        };
    }

    @Data
    public static class config{
        private boolean isEnabled;
    }
}
