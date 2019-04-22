package com.jimbolix.shield.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 自定义的userDetailService
 * 功能：处理用户信息的获取逻辑
 *
 */
@Component
public class ShieldUserDetailsService implements UserDetailsService, SocialUserDetailsService {
   @Autowired
   private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //todo 根据用户名进行数据库查询
        //todo 查询出的用户密码直接作为参数构建UserDetails的实现类，spring security会通过相应的机制来比对用户密码和页面输入的密码
        User user = new User(userName,passwordEncoder.encode("12345"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
