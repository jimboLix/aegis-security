package com.jimbolix.shield.brower.controller;

import com.jimbolix.shield.brower.support.SimpleResponse;
import com.jimbolix.shield.core.properties.BrowserConfigProperties;
import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来处理用户请求
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//    @Autowired
//    private BrowserConfigProperties browserConfigProperties;
    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;
    @RequestMapping("/reuire")
    public SimpleResponse reuireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(null != savedRequest){
            String targetUrl = savedRequest.getRedirectUrl();
            //如果是html请求，怎返回登录页面
            if(!StringUtils.isEmpty(targetUrl) && StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                redirectStrategy.sendRedirect(request,response,shieldSecurityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("请登陆");
    }

}
