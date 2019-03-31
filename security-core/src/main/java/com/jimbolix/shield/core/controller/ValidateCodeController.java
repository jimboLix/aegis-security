package com.jimbolix.shield.core.controller;

import com.jimbolix.shield.core.support.Constant;
import com.jimbolix.shield.core.validate.ValidateImageCodeGenerator;
import com.jimbolix.shield.core.validate.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateImageCodeGenerator generateImageCode;


    @GetMapping("/image/code")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成随机验证码
        ImageCode imageCode = generateImageCode.createImageCode();
        //将验证码存到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), Constant.IMAGE_CODE_SESSION_KEY,imageCode);
        //将图片写入响应中
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }


}
