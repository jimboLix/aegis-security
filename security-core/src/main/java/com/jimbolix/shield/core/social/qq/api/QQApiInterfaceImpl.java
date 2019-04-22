package com.jimbolix.shield.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 12:13
 * @Description:
 */
public class QQApiInterfaceImpl extends AbstractOAuth2ApiBinding implements QQApiInterface {

    Logger logger = LoggerFactory.getLogger(QQApiInterfaceImpl.class);

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQApiInterfaceImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("从qq获取的结果是【"+result+"】");
        //查看qq互联中返回的结果是callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
        logger.info("获取到的openId是"+this.openId);
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO,appId,openId);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("获取用户信息得到的结果是【"+result+"】");
        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
        }catch (Exception e){
            throw new RuntimeException("获取用户信息失败");
        }
        return userInfo;
    }

}
