package com.util;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Objects;

/**
 * @author Q版小李
 * @description
 * @create 2021/2/23 18:23
 */
public class WaterMarkUtil {

    private static final String FONT_NAME = "宋体";

    private static final int FONT_STYLE = Font.BOLD;

    private static final int FONT_SIZE = 50;

    private static final Color FONT_COLOR = Color.gray;

    private static final float ALPHA = 0.3F;

    /**
     * 给图片添加单条文字水印
     *
     * @param multipartFile    需要添加水印的图片
     * @param waterMarkContent 水印的文字
     */
    @SneakyThrows
    public static byte[] textWaterMark(MultipartFile multipartFile, String waterMarkContent) {
        int X = 636;
        int Y = 700;
        Image image = ImageIO.read(multipartFile.getInputStream());
        //计算原始图片宽度长度
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        //创建图片缓存对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //创建java绘图工具对象
        Graphics2D graphics2d = bufferedImage.createGraphics();
        //参数主要是，原图，坐标，宽高
        graphics2d.drawImage(image, 0, 0, width, height, null);
        graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
        graphics2d.setColor(FONT_COLOR);
        //使用绘图工具将水印绘制到图片上
        //计算文字水印宽高值
        int waterWidth = FONT_SIZE * getTextLength(waterMarkContent);
        //计算水印与原图高宽差
        int widthDiff = width - waterWidth;
        int heightDiff = height - FONT_SIZE;
        //水印坐标设置
        if (X > widthDiff) {
            X = widthDiff;
        }
        if (Y > heightDiff) {
            Y = heightDiff;
        }
        //水印透明设置
        graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
        graphics2d.drawString(waterMarkContent, X, Y + FONT_SIZE);
        graphics2d.dispose();
        // 输出图片
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 给图片添加单图片水印
     *
     * @param multipartFile  需要添加水印的图片
     * @param waterMarkImage 水印图片路径
     */
    @SneakyThrows
    public static byte[] imageWaterMark(MultipartFile multipartFile, String waterMarkImage) {
        int X = 636;
        int Y = 763;
        Image image = ImageIO.read(multipartFile.getInputStream());
        //计算原始图片宽度长度
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        //创建图片缓存对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //创建java绘图工具对象
        Graphics2D graphics2d = bufferedImage.createGraphics();
        //参数主要是，原图，坐标，宽高
        graphics2d.drawImage(image, 0, 0, width, height, null);
        graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
        graphics2d.setColor(FONT_COLOR);
        //水印图片路径
        Image imageLogo = ImageIO.read(new URL(waterMarkImage));
        int widthLogo = imageLogo.getWidth(null);
        int heightLogo = imageLogo.getHeight(null);
        int widthDiff = width - widthLogo;
        int heightDiff = height - heightLogo;
        //水印坐标设置
        if (X > widthDiff) {
            X = widthDiff;
        }
        if (Y > heightDiff) {
            Y = heightDiff;
        }
        //水印透明设置
        graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
        graphics2d.drawImage(imageLogo, X, Y, null);
        graphics2d.dispose();
        // 输出图片
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 给图片添加多条文字水印
     *
     * @param multipartFile    需要添加水印的图片
     * @param waterMarkContent 水印的文字
     */
    @SneakyThrows
    public static byte[] moreTextWaterMark(MultipartFile multipartFile, String waterMarkContent) {
        Image image = ImageIO.read(multipartFile.getInputStream());
        //计算原始图片宽度长度
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        //创建图片缓存对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //创建java绘图工具对象
        Graphics2D graphics2d = bufferedImage.createGraphics();
        //参数主要是，原图，坐标，宽高
        graphics2d.drawImage(image, 0, 0, width, height, null);
        graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
        graphics2d.setColor(FONT_COLOR);
        //使用绘图工具将水印绘制到图片上
        //计算文字水印宽高值
        int waterWidth = FONT_SIZE * getTextLength(waterMarkContent);
        //水印透明设置
        graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
        graphics2d.rotate(Math.toRadians(30), bufferedImage.getWidth() >> 1, bufferedImage.getHeight() >> 1);
        int x = -width / 2;
        int y;
        while (x < width * 1.5) {
            y = -height / 2;
            while (y < height * 1.5) {
                graphics2d.drawString(waterMarkContent, x, y);
                y += FONT_SIZE + 100;
            }
            x += waterWidth + 100;
        }
        graphics2d.dispose();
        // 输出图片
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 给图片添加多个图片水印
     *
     * @param multipartFile  需要添加水印的图片
     * @param waterMarkImage 水印图片路径
     */
    @SneakyThrows
    public static byte[] moreImageWaterMark(MultipartFile multipartFile, String waterMarkImage) {
        Image image = ImageIO.read(multipartFile.getInputStream());
        //计算原始图片宽度长度
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        //创建图片缓存对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //创建java绘图工具对象
        Graphics2D graphics2d = bufferedImage.createGraphics();
        //参数主要是，原图，坐标，宽高
        graphics2d.drawImage(image, 0, 0, width, height, null);
        graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
        graphics2d.setColor(FONT_COLOR);
        //水印图片路径
        Image imageLogo = ImageIO.read(new URL(waterMarkImage));
        int widthLogo = imageLogo.getWidth(null);
        int heightLogo = imageLogo.getHeight(null);
        //水印透明设置
        graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
        graphics2d.rotate(Math.toRadians(30), bufferedImage.getWidth() >> 1, bufferedImage.getHeight() >> 1);
        int x = -width / 2;
        int y;
        while (x < width * 1.5) {
            y = -height / 2;
            while (y < height * 1.5) {
                graphics2d.drawImage(imageLogo, x, y, null);
                y += heightLogo + 100;
            }
            x += widthLogo + 100;
        }
        graphics2d.dispose();
        // 输出图片
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // 计算水印文本长度
    //1、中文长度即文本长度 2、英文长度为文本长度二分之一
    private static int getTextLength(String text) {
        //水印文字长度
        int length = text.length();

        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }

}
