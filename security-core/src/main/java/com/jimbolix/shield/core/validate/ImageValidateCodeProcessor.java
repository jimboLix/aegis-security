package com.jimbolix.shield.core.validate;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @Auther: admin
 * @Date: 2019/4/7 09:48
 * @Description:
 */
@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        //将图片写入响应中
        ImageIO.write(validateCode.getImage(),"JPEG",request.getResponse().getOutputStream());
    }
}
