package com.jimbolix.shield.core.social.qq.connect;

import com.jimbolix.shield.core.social.qq.api.QQApiInterface;
import com.jimbolix.shield.core.social.qq.api.QQApiInterfaceImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 15:05
 * @Description: qq service provider
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApiInterface> {

    private String appId;

    private final static String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    private final static String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     * @param clientId 应用的唯一标识
     * @param clientSecret 应用的密
     */
    public QQServiceProvider(String clientId, String clientSecret) {
        super(new OAuth2Template(clientId,clientSecret,AUTHORIZE_URL,ACCESS_TOKEN_URL));
    }

    @Override
    public QQApiInterface getApi(String accessToken) {
        return new QQApiInterfaceImpl(accessToken,appId);
    }
}
