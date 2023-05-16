package com.theceres.webfluxtest.config;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class AdminAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String userIp;

    public AdminAuthenticationToken(Object principal, Object credentials, String userIp) {
        super(principal, credentials);
        this.userIp = userIp;
    }
}
