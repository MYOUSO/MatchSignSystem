package com.matchsystem.matchsystem.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


//不会写，CV工程师
@RestController
public class CaptchaController {

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 200;
        int height = 50;

        // 创建一个图像对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 生成随机验证码
        String captcha = generateCaptcha();
        // 将验证码存储在session中
        session.setAttribute("captcha", captcha);

        // 设置背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置字体和颜色
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        g2d.drawString(captcha, 50, 30);

        // 输出图像
        ImageIO.write(image, "jpeg", response.getOutputStream());
        g2d.dispose();
    }

    private String generateCaptcha() {
        // 生成一个6位的随机数字验证码
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            captcha.append(random.nextInt(10));
        }
        return captcha.toString();
    }
}
