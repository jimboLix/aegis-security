package com.jimbolix.shield.core.social.qq.config;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.properties.SocialConfigProperties;
import com.jimbolix.shield.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 16:18
 * @Description:
 */
@Configuration
@ConditionalOnProperty(prefix = "shield.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private ShieldSecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        SocialConfigProperties social = securityProperties.getSocial();
        QQConnectionFactory qqConnectionFactory = new QQConnectionFactory(social.getQq().getProviderId(), social.getQq().getAppId(), social.getQq().getAppSecret());
        connectionFactoryConfigurer.addConnectionFactory(qqConnectionFactory);
    }
}
