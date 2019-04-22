package com.jimbolix.shield.core.social;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 15:41
 * @Description:
 */
@Configuration
@EnableSocial
public class ShieldSocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ShieldSecurityProperties securityProperties;
    /**
     * 配置连接数据库的信息
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        jdbcUsersConnectionRepository.setTablePrefix("");
        return jdbcUsersConnectionRepository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public SpringSocialConfigurer shieldspringSocialConfigurer(){
        ShiedSpringSocialConfig shiedSpringSocialConfig = new ShiedSpringSocialConfig(securityProperties.getSocial().getQq().getFilterProcessesUrl());
        return shiedSpringSocialConfig;
    }

}
