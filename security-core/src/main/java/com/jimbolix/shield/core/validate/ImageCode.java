package com.jimbolix.shield.core.validate;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * 图形验证码实体类
 */
@Data
public class ImageCode extends ValidateCode{

    private BufferedImage image;

    /**
     *
     * @param image
     * @param code
     * @param expireIn 超时时间数
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }
}
