package com.theceres.webfluxtest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        String userIp = "";
        log.info("access authenticate");

        if (authentication instanceof AdminAuthenticationToken) {
            userIp = ((AdminAuthenticationToken) authentication).getUserIp();
            log.info("userip : {}, username : {}", userIp, username);
        }

        return Mono.just(new UsernamePasswordAuthenticationToken(username, null, null));
    }
}
