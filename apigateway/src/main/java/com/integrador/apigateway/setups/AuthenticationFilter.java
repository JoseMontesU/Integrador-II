package com.integrador.apigateway.setups;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    private final WebClient.Builder webclientBuilder;

    public AuthenticationFilter(WebClient.Builder webclientBuilder) {
        super(Config.class);
        this.webclientBuilder = webclientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.info("no hay autorizacion");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing  Authorization header");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                log.info("Mala estructura de autorizacion");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Authorization structure");
            }
            return  webclientBuilder.build()
                    .get()
                    .uri("http://keycloack/roles").header(HttpHeaders.AUTHORIZATION, parts[1])
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .map(response -> {
                        if(response != null){
                            log.info("See Objects: " + response);
                            //check for realm-admin rol
                            if(response.get("user") == null || StringUtils.isEmpty(response.get("user").asText())){
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Role user missing");
                            }
                        }else{
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
                        }
                        log.info("Exchage: "+exchange.getResponse());
                        return exchange;
                    })
                    .onErrorMap(error -> {
                        log.info("error de comunicacion");
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Communication Error", error.getCause());})
                    .flatMap(chain::filter);
        },1);
    }

    public static class Config{
    }
}
