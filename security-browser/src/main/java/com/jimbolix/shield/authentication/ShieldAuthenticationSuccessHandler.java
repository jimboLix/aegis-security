package com.jimbolix.shield.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.support.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户登陆成功后的处理器
 */
@Component("shieldAuthenticationSuccessHandler")
public class ShieldAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //首先判断配置的跳转类型
        if(shieldSecurityProperties.getBrowser().getLoginType().equals(LoginType.JSON)){
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(authentication));
        }else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
