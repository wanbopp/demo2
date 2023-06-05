package com.example.piccdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/2/27 13:44
 * @注释
 */
@Controller
@RequestMapping("/photo")
public class WaterMark {
    private static final Logger logger = LoggerFactory.getLogger(WaterMark.class);
    @RequestMapping
    public String addWaterMark() throws IOException {

        // 读取原始图片
        File originalImageFile = new File("originalImage.jpg");
        BufferedImage originalImage = ImageIO.read(originalImageFile);

        // 创建一个新的BufferedImage对象，大小与原始图片相同
        BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 获取Graphics2D对象，并在水印图像上绘制原始图像
        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();
        g2d.drawImage(originalImage, 0, 0, null);

        // 添加水印文本
        String watermarkText = "my watermark";
        Font font = new Font("Arial", Font.BOLD, 36);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        int x = (watermarkedImage.getWidth() - g2d.getFontMetrics().stringWidth(watermarkText)) / 2;
        int y = watermarkedImage.getHeight() / 2;
        g2d.drawString(watermarkText, x, y);

        // 保存水印图片
        File watermarkedImageFile = new File("watermarkedImage.jpg");
        ImageIO.write(watermarkedImage, "jpg", watermarkedImageFile);

        // 释放资源
        g2d.dispose();
        return watermarkText;
    }


}
