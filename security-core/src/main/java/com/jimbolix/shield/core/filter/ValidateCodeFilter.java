package com.jimbolix.shield.core.filter;

import com.jimbolix.shield.core.properties.ShieldSecurityProperties;
import com.jimbolix.shield.core.support.SecurityConstants;
import com.jimbolix.shield.core.validate.ValidateCodeProcessor;
import com.jimbolix.shield.core.validate.ValidateCodeProcessorHolder;
import com.jimbolix.shield.core.validate.ValidateCodeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/14 15:07
 * @Description:
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private Map<String, ValidateCodeType> urlTypeMap = new HashMap<>();

    @Autowired
    private ShieldSecurityProperties shieldSecurityProperties;
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 建立url与验证码类型之间的映射关系
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //图形验证码
        List<String> urls = new ArrayList<>();
        urls.add(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);
        urls.addAll(shieldSecurityProperties.getValidateCode().getImageCodeProperties().getUrls());
        this.addUrlToMap(urls,ValidateCodeType.IMAGE);

        //手机验证
        List<String> mobileUrls = new ArrayList<>();
        mobileUrls.add(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE);
        mobileUrls.addAll(shieldSecurityProperties.getValidateCode().getSmsCodeProperties().getUrls());
        this.addUrlToMap(mobileUrls,ValidateCodeType.SMS);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = this.getValidateCodeType(request);
        //判断当前请求是否需要验证码验证
        if(null != type){
            //获取处理器类型
            ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.findValidateCodeProcessor(type);
            validateCodeProcessor.valicate(new ServletWebRequest(request));
        }
        filterChain.doFilter(request,response);
    }

    private void addUrlToMap(List<String> urls, ValidateCodeType type){
        urls.forEach(url ->{
            urlTypeMap.put(url,type);
        });
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest request){
        if(StringUtils.isNotEmpty(request.getRequestURI()) && !StringUtils.endsWithIgnoreCase(request.getMethod(),"get")){
            Set<String> urls = urlTypeMap.keySet();
            for (String url : urls){
                if(antPathMatcher.match(url,request.getRequestURI())){
                    return urlTypeMap.get(url);
                }
            }
        }
        return null;
    }
}
