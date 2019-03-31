package com.jimbolix.shield.core.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证码验证异常
 */
public class ValiDateCodeException extends AuthenticationException {
    private static final long serialVersionUID = 7448169915986281124L;

    public ValiDateCodeException(String msg) {
        super(msg);
    }
}
