package com.jimbolix.shield.core.filter;

import com.jimbolix.shield.core.exception.ValiDateCodeException;
import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.support.Constant;
import com.jimbolix.shield.core.validate.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 验证图形验证码的过滤器
 */
public class ImageCodeValidateCodeFilter extends OncePerRequestFilter {

    //配置自定义的登陆失败处理器
    private AuthenticationFailureHandler authenticationFailureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private ShieldSecurityProperties shieldSecurityProperties;
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        //先验证是不是登陆验证
        List<String> urls = shieldSecurityProperties.getValidateCode().getImageCodeProperties().getUrls();
        urls.add("/authentication/form");
        boolean action = false;
        for (String url : urls){
            if(pathMatcher.match(url,httpServletRequest.getRequestURI())){
                action = true;
            }
        }
        if(action) {
           try {
               valicate(httpServletRequest);
           }catch (ValiDateCodeException e){
               //异常调用验证失败的处理器
               authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
               return;
           }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void valicate(HttpServletRequest httpServletRequest) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(new ServletWebRequest(httpServletRequest), Constant.IMAGE_CODE_SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(httpServletRequest, "code");
        //判断用户是否输入了验证码
        if(StringUtils.isEmpty(codeInRequest)){
            throw new ValiDateCodeException("请输入验证码");
        }else if(null == codeInSession){ //判断验证码是否存在
            throw new ValiDateCodeException("验证码不存在");
        }else if(codeInSession.isExpired()){ //判断验证码是否已经超期
            sessionStrategy.removeAttribute(new ServletWebRequest(httpServletRequest),Constant.IMAGE_CODE_SESSION_KEY);
            throw new ValiDateCodeException("验证码已超时");
        }else if(!StringUtils.equalsIgnoreCase(codeInRequest,codeInSession.getCode())){//判断验证码是否正确
            throw new ValiDateCodeException("验证码不正确");
        }

        sessionStrategy.removeAttribute(new ServletWebRequest(httpServletRequest),Constant.IMAGE_CODE_SESSION_KEY);
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setShieldSecurityProperties(ShieldSecurityProperties shieldSecurityProperties) {
        this.shieldSecurityProperties = shieldSecurityProperties;
    }
}
