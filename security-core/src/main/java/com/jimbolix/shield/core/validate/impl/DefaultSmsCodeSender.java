package com.jimbolix.shield.core.validate.impl;

import com.jimbolix.shield.core.validate.SmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Auther: ruihui.li
 * @Date: 2019/4/7 09:56
 * @Description: 默认的短信验证码发送
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    Logger logger = LoggerFactory.getLogger(DefaultSmsCodeSender.class);
    @Override
    public void sendCode(String mobile, String code) {
        logger.info("向手机"+mobile+"发送验证码"+code);
    }
}
