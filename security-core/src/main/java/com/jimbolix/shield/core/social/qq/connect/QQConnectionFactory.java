package com.jimbolix.shield.core.social.qq.connect;

import com.jimbolix.shield.core.social.qq.api.QQApiInterface;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 15:33
 * @Description: qq登陆需要的ConnectionFactory
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApiInterface> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     */
    public QQConnectionFactory(String providerId,String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret),new QQApiAdapter());
    }
}
