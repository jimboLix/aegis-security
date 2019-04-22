package com.jimbolix.shield.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 16:35
 * @Description:
 */
public class ShiedSpringSocialConfig extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public ShiedSpringSocialConfig(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
