package com.example.piccdemo.controller;

import net.coobird.thumbnailator.Thumbnails;

import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

@Controller
public class WatermarkController {
    @Value("${image.path}")
    private String imagePath;

    @PostMapping("/addWatermark")
    public ResponseEntity<Resource> addWatermark(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("watermark") MultipartFile watermark) {
        try {
            // 将原图片和水印图片保存到临时文件
            File inputFile = createTempFile(file);
            File watermarkFile = createTempFile(watermark);

            // 为原图片添加水印
//            String outputFilePath = addWatermark(inputFile.getAbsolutePath(), watermarkFile.getAbsolutePath());
            String outputFilePath = null;
            // 将加水印的图片返回给客户端
            Path path = Paths.get(outputFilePath);
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok()
                    .contentLength(Files.size(path))
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/addWatermark1")
    public ResponseEntity<Resource> addWatermarkV1(@RequestParam("inputFilePath") MultipartFile inputFilePath, @RequestParam("watermarkFilePath") MultipartFile watermarkFilePath) throws Exception {
        // 将原图片和水印图片保存到临时文件
        File inputFile = createTempFile(inputFilePath);
        File watermarkFile = createTempFile(watermarkFilePath);
        String filePath = "C:\\Users\\wanbo_pp\\Desktop\\aim\\123.jpg";
        Thumbnails.of(inputFile)
                // 水印放到右下角
                .watermark(Positions.TOP_LEFT, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.TOP_CENTER, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.TOP_RIGHT, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.CENTER_LEFT, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.CENTER, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.CENTER_RIGHT, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.BOTTOM_LEFT, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.BOTTOM_CENTER, ImageIO.read(watermarkFile), 0.5f)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermarkFile), 0.5f)
                .scale(1.74)
                .toFile(filePath);
        Path path = Paths.get(filePath);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .contentLength(Files.size(path))
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(resource);
    }

    // 创建临时文件
    private File createTempFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("prefix-", "-suffix");
        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(file.getBytes());
        }
        return tempFile;
    }


    //水印颜色
    private static Color markContentColor = Color.RED;
    //水印字体，大小
    private static Font font = new Font("宋体", Font.BOLD, 18);
    //设置水印文字的旋转角度
    private static Integer degree = 45;
    //设置水印透明度
    private static float alpha = 0.1f;
    // 水印之间的间隔
    private static final int XMOVE = 40;
    // 水印之间的间隔
    private static final int YMOVE = 60;

    /**
     * 添加图片水印平铺
     *
     * @param srcImageFile   目标图片
     * @param waterImageFile 水印图片
     * @return
     */
    public static boolean addImageWatermark(File srcImageFile, File waterImageFile) {
        try {

            Image srcImg = ImageIO.read(srcImageFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            Image waterImage = ImageIO.read(waterImageFile);//文件转化为图片
            int waterImageWidth = waterImage.getWidth(null);//获取水印图片的宽
            int waterImageHeight = waterImage.getHeight(null);

            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            BufferedImage image = ImageIO.read(waterImageFile);
            //获取画布
            Graphics2D graphics = read.createGraphics();
            //设置透明度为0.5
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            if (waterImageWidth < 100) {
                waterImageWidth = 200;
            }
            waterImageWidth += XMOVE;
            int rowsNumber = srcImgHeight / waterImageWidth;// 图片的高  除以  文字水印的宽    ——> 打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth / waterImageWidth;//图片的宽 除以 文字水印的宽   ——> 每行打印的列数(以文字水印的宽为间隔)
            /*//防止图片太小而文字水印太长，所以至少打印一次
            if(rowsNumber < 1){
                rowsNumber = 1;
            }
            if(columnsNumber < 1){
                columnsNumber = 1;
            }*/
            for (int j = 0; j < rowsNumber; j++) {
                for (int i = 0; i < columnsNumber; i++) {
                    int x = i * waterImageWidth + j * waterImageWidth;
                    int y = -i * waterImageWidth + j * waterImageWidth + YMOVE;
                    //添加水印
                    graphics.drawImage(image, x, y, null);
                }
            }
            //关闭透明度
            //graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出-覆盖原图
            ImageIO.write(read, formatName, srcImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 1、倾斜图片
     * 2、构造一个空白的图层（大小判断）
     * 3、
     *
     * @param srcImageFile
     * @param waterImageFile
     */
    public static void addImageWatermarkV2(File srcImageFile, File waterImageFile) throws IOException {

        Image srcImg = ImageIO.read(srcImageFile);//文件转化为图片
        int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
        int srcImgHeight = srcImg.getHeight(null);//获取图片的高

        Image waterImage = ImageIO.read(waterImageFile);//文件转化为图片
        int waterImageWidth = waterImage.getWidth(null);//获取水印图片的宽
        int waterImageHeight = waterImage.getHeight(null);

        //旋转水印

        //使用ImageIO的read方法读取图片
        BufferedImage srcPhoto = ImageIO.read(srcImageFile);
        BufferedImage waterPhoto = ImageIO.read(waterImageFile);
        //获取空白画布 高宽比图片大 还是水印图片的整数被
        int width = (int) ((Math.ceil(srcImgWidth / waterImageWidth) + 1) * waterImageWidth);
        int height = (int) ((Math.ceil(srcImgHeight / waterImageHeight) + 1) * waterImageHeight);
        BufferedImage backImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D graphics = backImage.createGraphics();
        //绘画目标图片
        graphics.drawImage(srcPhoto, 0, 0, null);
        //设置透明度
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
//        旋转
        graphics.rotate(-45);
        //循环绘制水印
        for (int i = 0; i < Math.ceil(srcImgHeight / waterImageHeight); i++) {
            for (int j = 0; j < Math.ceil(srcImgWidth / waterImageWidth); j++) {
                graphics.drawImage(waterPhoto, j * waterImageWidth, i * waterImageHeight, null);
            }
        }
        graphics.rotate(315);


    }


    public static void main(String[] args) throws IOException {
        File carFile = new File("C:\\Users\\wanbo_pp\\Desktop\\aim\\car4.jpg");
        File waterFile = new File("C:\\Users\\wanbo_pp\\Desktop\\aim\\water.png");
//
        addImageWatermarkV3(carFile, waterFile);
//        System.out.println("Math.ceil(1500 / 1000) = " + Math.ceil(2000 / 1000));

    }


    /**
     *
     * V3
     * @param srcImageFile
     * @param waterImageFile
     */
    public static void addImageWatermarkV3(File srcImageFile, File waterImageFile) throws IOException {

        Image srcImg = null ;
        try {
            srcImg = ImageIO.read(srcImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
        int srcImgHeight = srcImg.getHeight(null);//获取图片的高
        Image waterImage = ImageIO.read(waterImageFile);//文件转化为图片
        int waterImageWidth = waterImage.getWidth(null);//获取水印图片的宽
        int waterImageHeight = waterImage.getHeight(null);
        //使用ImageIO的read方法读取图片
        BufferedImage srcPhoto = ImageIO.read(srcImageFile);
        BufferedImage waterPhoto = ImageIO.read(waterImageFile);
        //获取空白画布 求图片对角线长度
        double sqrt = Math.sqrt((Math.pow(srcImgHeight, 2)) + (Math.pow(srcImgWidth, 2)));
        int length = (int) Math.ceil(sqrt);
        BufferedImage backImage = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = backImage.createGraphics();
        //绘画目标图片 将图片绘制在中央
        int x = (int) Math.ceil((length - srcImgWidth) / 2);
        int y = (int) Math.ceil((length - srcImgHeight) / 2);
        graphics.drawImage(srcPhoto, x, y, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置透明度
        graphics.rotate(-45, (int) (length / 2), (int) (length / 2));//以画板中心旋转  logo倾斜角度可以设定
        //通过边长计算
        int xNum = (int) Math.ceil(length / waterImageWidth) + 1;
        int yNUm = (int) Math.ceil(length / waterImageHeight) + 1;
        //循环绘制水印
        for (int i = 0; i < yNUm; i++) {
            for (int j = 0; j < xNum; j++) {
                graphics.drawImage(waterPhoto, j * waterImageWidth, i * waterImageHeight, null);
            }
        }
        BufferedImage cutImage = backImage.getSubimage(x, y, srcImgWidth, srcImgHeight);
        graphics.dispose();//关闭资源
        ImageIO.write(cutImage, "jpg", Files.newOutputStream(Paths.get("C:\\Users\\wanbo_pp\\Desktop\\aim\\aim.jpg")));//输出到指定位置
    }
}

