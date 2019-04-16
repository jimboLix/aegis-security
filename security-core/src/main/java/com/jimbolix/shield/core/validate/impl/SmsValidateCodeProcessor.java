package com.jimbolix.shield.core.validate.impl;

import com.jimbolix.shield.core.validate.SmsCodeSender;
import com.jimbolix.shield.core.validate.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Auther: admin
 * @Date: 2019/4/7 10:00
 * @Description:
 */
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.sendCode(mobile,validateCode.getCode());
    }
}
