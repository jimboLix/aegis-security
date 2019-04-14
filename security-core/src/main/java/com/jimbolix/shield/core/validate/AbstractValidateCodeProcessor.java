package com.jimbolix.shield.core.validate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;


@Component
public abstract class AbstractValidateCodeProcessor<T> implements ValidateCodeProcessor {

    private final String SESSION_KEY_PREFIX = "session_key_prefix";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGeneratorMap;
    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request,validateCode);
        send(request,validateCode);
    }

    private T generate(ServletWebRequest request){
        String type = getProcessorType(request).toString().toLowerCase();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "CodeGenerator");
        return (T) validateCodeGenerator.createCode();
    }
    private void save(ServletWebRequest webRequest,T validateCode){
        sessionStrategy.setAttribute(webRequest, SESSION_KEY_PREFIX+getProcessorType(webRequest),validateCode);
    }
    protected abstract void send(ServletWebRequest request,T validateCode) throws Exception;

    private String getProcessorType(ServletWebRequest webRequest){
        return StringUtils.substringAfter(webRequest.getRequest().getRequestURI(),"/code/");
    }
}
