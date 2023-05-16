package com.theceres.webfluxtest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class AdminAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String userIp = getClientIp(request);

        log.debug("userIp : {}", userIp);

        return exchange.getFormData()
                .map(body -> {
                    String username = body.getFirst("username");
                    String password = body.getFirst("password");

                    return new AdminAuthenticationToken(username, password, userIp);
                });
    }

    private String getClientIp(ServerHttpRequest request) {
        return request.getRemoteAddress() != null
                ? request.getRemoteAddress().toString()
                : "";
    }
}
