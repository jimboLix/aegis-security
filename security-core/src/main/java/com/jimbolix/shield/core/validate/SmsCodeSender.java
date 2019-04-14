package com.jimbolix.shield.core.validate;

/**
 * @Auther: admin
 * @Date: 2019/4/7 09:54
 * @Description: 短信验证码发送接口
 */
public interface SmsCodeSender {
     void sendCode(String mobile,String code);
}
