package com.jimbolix.shield.core.validate;

import com.jimbolix.shield.core.support.SecurityConstants;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/14 15:11
 * @Description:
 */
public enum ValidateCodeType {

    /**
     * 短信验证码类型
     */
    SMS{
        public  String getParamNameOnValidate(){
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图形验证码类型
     */
    IMAGE{
        public  String getParamNameOnValidate(){
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };
    public abstract String getParamNameOnValidate();
}
