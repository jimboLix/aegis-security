package com.jimbolix.shield.securitydemo;

import com.jimbolix.shield.core.validate.ImageCode;
import com.jimbolix.shield.core.validate.ValidateImageCodeGenerator;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NewValidateCodeGenerator implements ValidateImageCodeGenerator {
    @Override
    public ImageCode createImageCode() {
        BufferedImage image = new BufferedImage(64, 32, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, 64, 32);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        ImageCode imageCode = new ImageCode(image,"aaaa",60);
        return imageCode;
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
