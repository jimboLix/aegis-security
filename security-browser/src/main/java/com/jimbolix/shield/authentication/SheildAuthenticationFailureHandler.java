package com.jimbolix.shield.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.support.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("sheildAuthenticationFailureHandler")
public class SheildAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(LoginType.JSON.equals(shieldSecurityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(exception));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
