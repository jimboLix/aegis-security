package com.jimbolix.shield.core.config;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ShieldSecurityProperties.class})
public class ShieldCoreConfig {
}
