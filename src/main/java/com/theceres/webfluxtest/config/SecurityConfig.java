package com.theceres.webfluxtest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig implements WebFluxConfigurer {
    public static final String LOGIN_PAGE = "/signin";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager authenticationManager) {
        ServerSecurityContextRepository serverSecurityContextRepository = new WebSessionServerSecurityContextRepository();

        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers("/rtb").permitAll()
                .and()
                .addFilterAt(authenticationFilter(serverSecurityContextRepository, authenticationManager), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    private AuthenticationWebFilter authenticationFilter(ServerSecurityContextRepository serverSecurityContextRepository, ReactiveAuthenticationManager authenticationManager) {
        ServerAuthenticationConverter bearerConverter = new AdminAuthenticationConverter();
        AuthenticationWebFilter bearerAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);

        bearerAuthenticationFilter.setServerAuthenticationConverter(bearerConverter);
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, LOGIN_PAGE));
        bearerAuthenticationFilter.setSecurityContextRepository(serverSecurityContextRepository);

        return bearerAuthenticationFilter;
    }
}
