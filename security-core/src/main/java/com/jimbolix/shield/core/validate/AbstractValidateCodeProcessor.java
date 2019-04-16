package com.jimbolix.shield.core.validate;

import com.jimbolix.shield.core.exception.ValiDateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

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

    @Override
    public void valicate(ServletWebRequest request){
            //1.获取类型，用以确定request中验证码参数名
        String type = getProcessorType(request);
        if(StringUtils.isEmpty(type)){
            return;
        }
        //2.获取sessionKey
        String key = getSessionKey(request);
        if(StringUtils.isEmpty(key)){
            return;
        }
        //3.存在session中的验证码
        T codeInSession = (T) sessionStrategy.getAttribute(request, key);
        //4。获取request中的验证码
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), getValidateCodeType().getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw  new ValiDateCodeException("获取验证码失败");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValiDateCodeException(type + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValiDateCodeException(type + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, key);
            throw new ValiDateCodeException(type + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValiDateCodeException(type + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, key);


    }

    private T generate(ServletWebRequest request){
        String type = getProcessorType(request).toString().toLowerCase();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "CodeGenerator");
        return (T) validateCodeGenerator.createCode();
    }
    private void save(ServletWebRequest webRequest,T validateCode){
        sessionStrategy.setAttribute(webRequest, getSessionKey(webRequest),validateCode);
    }
    protected abstract void send(ServletWebRequest request,T validateCode) throws Exception;

    private String getProcessorType(ServletWebRequest webRequest){
        return StringUtils.substringAfter(webRequest.getRequest().getRequestURI(),"/code/");
    }

    private String getSessionKey(ServletWebRequest request){
        return SESSION_KEY_PREFIX+this.getProcessorType(request).toUpperCase();
    }

    private ValidateCodeType getValidateCodeType(){
        String type = StringUtils.substringBefore(this.getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type);
    }
}
