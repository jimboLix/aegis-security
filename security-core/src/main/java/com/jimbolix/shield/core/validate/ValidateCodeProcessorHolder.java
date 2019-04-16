package com.jimbolix.shield.core.validate;

import com.jimbolix.shield.core.exception.ValiDateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/14 15:49
 * @Description:
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String,ValidateCodeProcessor> processorMap;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){
        return this.findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        String name = type.toLowerCase()+ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = processorMap.get(name);
        if(null == validateCodeProcessor){
            throw  new ValiDateCodeException("验证码处理器"+name+"不存在");
        }
        return validateCodeProcessor;
    }
}
