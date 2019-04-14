package com.jimbolix.shield.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Auther: admin
 * @Date: 2019/4/7 19:50
 * @Description:
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //将authentication转为MobileAuthenticationToken对象
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        //获取手机号
        String  mobile = (String) mobileAuthenticationToken.getPrincipal();
        //根据手机号载入用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if(null == userDetails){
            throw new InternalAuthenticationServiceException("用户信息不存在");
        }
        //若获取到了用户信息则重新构造MobileAuthenticationToken对象，此对象是已验证过的
        MobileAuthenticationToken authenticationResult  = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
