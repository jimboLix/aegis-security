package com.jimbolix.shield.core.properties;

import lombok.Data;

/**
 * @Auther: ruihui.li
 * @Date: 2019/4/21 16:11
 * @Description:
 */
@Data
public class QQProperties {

    private String providerId ="qq";
    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    private String filterProcessesUrl;
}
