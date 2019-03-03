package com.jimbolix.shield.securitybrowser.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义的userDetailService
 * 功能：处理用户信息的获取逻辑
 *
 */
public class ShieldUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //todo 根据用户名进行数据库查询
        //todo 查询出的用户密码直接作为参数构建UserDetails的实现类，spring security会通过相应的机制来比对用户密码和页面输入的密码
        User user = new User(userName,"12345", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
