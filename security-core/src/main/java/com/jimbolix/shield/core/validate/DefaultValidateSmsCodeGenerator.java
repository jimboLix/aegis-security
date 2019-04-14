package com.jimbolix.shield.core.validate;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Auther: admin
 * @Date: 2019/4/7 10:07
 * @Description: 默认的短信验证码生成器
 */
public class DefaultValidateSmsCodeGenerator implements ValidateCodeGenerator {
    private ShieldSecurityProperties shieldSecurityProperties;

    @Override
    public ValidateCode createCode() {
        int length = shieldSecurityProperties.getValidateCode().getSmsCodeProperties().getLength();
        String numeric = RandomStringUtils.randomNumeric(length);
        return new ValidateCode(numeric,shieldSecurityProperties.getValidateCode().getSmsCodeProperties().getExpireIn());
    }

    public void setShieldSecurityProperties(ShieldSecurityProperties shieldSecurityProperties) {
        this.shieldSecurityProperties = shieldSecurityProperties;
    }
}
