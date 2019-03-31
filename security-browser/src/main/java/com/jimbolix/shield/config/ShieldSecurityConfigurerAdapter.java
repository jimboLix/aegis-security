package com.jimbolix.shield.config;

import com.jimbolix.shield.core.filter.ValidateCodeFilter;
import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * 用于配置自定义spring security的配置
 */
@Configuration
public class ShieldSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private BrowserConfigProperties browserConfigProperties;
    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;

    /**
     * 配置密码加密的工具类
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //配置自定义的登陆成功处理器
    @Resource(name = "shieldAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    //配置自定义的登陆失败处理器
    @Resource(name = "sheildAuthenticationFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;
    /**
     *配置基于表单登陆的验证
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        validateCodeFilter.setShieldSecurityProperties(shieldSecurityProperties);
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class).formLogin()//配置登陆验证基于form表单
                .loginPage("/authentication/reuire")//这个是用来配置自定义登陆页面的位置的
                .loginProcessingUrl("/authentication/form")//配置登陆请求的地址
                .failureHandler(authenticationFailureHandler).successHandler(authenticationSuccessHandler)
                .and()
                .authorizeRequests().antMatchers("/login.html","/authentication/reuire",shieldSecurityProperties.getBrowser().getLoginPage(),"/image/code")
                .permitAll()//配置哪些请求不需要登陆
                .anyRequest().authenticated().and().csrf().disable();
//                .httpBasic();//配置基于http的验证

    }
}
