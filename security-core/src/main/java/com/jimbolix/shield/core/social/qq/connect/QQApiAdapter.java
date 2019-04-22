package com.jimbolix.shield.core.social.qq.connect;

import com.jimbolix.shield.core.social.qq.api.QQApiInterface;
import com.jimbolix.shield.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 15:37
 * @Description:
 */
public class QQApiAdapter implements ApiAdapter<QQApiInterface> {
    @Override
    public boolean test(QQApiInterface api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQApiInterface api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQApiInterface api) {
        return null;
    }

    @Override
    public void updateStatus(QQApiInterface api, String message) {

    }
}
