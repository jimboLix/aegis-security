package com.jimbolix.shield.core.config;

import com.jimbolix.shield.core.authentication.mobile.SmsAuthenticationFilter;
import com.jimbolix.shield.core.authentication.mobile.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/9 22:51
 * @Description: 配置自定义的 mobile相关的token及filter和provider
 */
@Component
public class MobileSecurityConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        super.configure(httpSecurity);
        SmsAuthenticationProvider provider = new SmsAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
        filter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthenticationSuccessHandler(successHandler);
        httpSecurity.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class).authenticationProvider(provider);
    }
}
