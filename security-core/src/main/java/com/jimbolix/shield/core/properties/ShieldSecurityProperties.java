package com.jimbolix.shield.core.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shield.security")
@Data
public class ShieldSecurityProperties {

    private BrowserConfigProperties browser = new BrowserConfigProperties();

    private ValidateCodeProperties validateCode = new ValidateCodeProperties();

}
