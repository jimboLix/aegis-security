package com.jimbolix.shield.core.validate;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 验证码实体类
 */
@Data
public class ValidateCode {
    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
