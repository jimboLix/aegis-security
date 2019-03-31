package com.jimbolix.shield.core.config;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.validate.DefaultValidateImageCodeGenerator;
import com.jimbolix.shield.core.validate.ValidateImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValicateCodeBeanConfig {

    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;

    @Bean
    @ConditionalOnMissingBean(ValidateImageCodeGenerator.class)
    public ValidateImageCodeGenerator validateImageCodeGenerator(){
        DefaultValidateImageCodeGenerator validateImageCodeGenerator = new DefaultValidateImageCodeGenerator();
        validateImageCodeGenerator.setShieldSecurityProperties(shieldSecurityProperties);
        return validateImageCodeGenerator;
    }
}
