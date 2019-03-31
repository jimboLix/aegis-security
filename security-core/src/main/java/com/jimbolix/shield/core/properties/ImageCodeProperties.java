package com.jimbolix.shield.core.properties;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class ImageCodeProperties {

    private int width = 64;
    private int height = 32;
    /**
     * 验证码的长度
     */
    private int length = 4;
    /**
     * 验证码超时时间
     */
    private int expireIn = 60;
    /**
     * 需要验证码的请求
     *
     */
    private List<String> urls;

}
