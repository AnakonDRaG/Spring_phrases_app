package com.SGW.Phrases.config.JWT

import org.springframework.security.core.AuthenticationException;

@Deprecated
class JwtAuthenticationException extends AuthenticationException {
    JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    JwtAuthenticationException(String msg) {
        super(msg);
    }
}