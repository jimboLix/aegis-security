package com.jimbolix.shield.core.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shield.security")
public class ShieldSecurityProperties {

    private BrowserConfigProperties browser = new BrowserConfigProperties();

    public BrowserConfigProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserConfigProperties browser) {
        this.browser = browser;
    }
}
