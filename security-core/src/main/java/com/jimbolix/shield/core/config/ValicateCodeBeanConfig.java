package com.jimbolix.shield.core.config;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.validate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValicateCodeBeanConfig {

    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;

    @Bean(name = "imageCodeGenerator")
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator validateImageCodeGenerator(){
        DefaultValidateImageCodeGenerator validateImageCodeGenerator = new DefaultValidateImageCodeGenerator();
        validateImageCodeGenerator.setShieldSecurityProperties(shieldSecurityProperties);
        return validateImageCodeGenerator;
    }
    @Bean(name = "smsCodeGenerator")
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public ValidateCodeGenerator smsValidateCodeGenerator(){
        DefaultValidateSmsCodeGenerator defaultValidateSmsCodeGenerator = new DefaultValidateSmsCodeGenerator();
        defaultValidateSmsCodeGenerator.setShieldSecurityProperties(shieldSecurityProperties);
        return defaultValidateSmsCodeGenerator;
    }
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
