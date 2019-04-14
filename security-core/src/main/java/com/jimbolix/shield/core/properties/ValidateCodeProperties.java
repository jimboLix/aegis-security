package com.jimbolix.shield.core.properties;

import lombok.Data;

/**
 * 验证码相关的配置类
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties imageCodeProperties = new ImageCodeProperties();

    private CodeProperties smsCodeProperties = new CodeProperties();
}
