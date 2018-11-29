package com.funcas.pboot.common.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月29日
 */
public class AuthException extends OAuth2Exception {

    public AuthException(String msg) {
        super(msg);
    }
}
