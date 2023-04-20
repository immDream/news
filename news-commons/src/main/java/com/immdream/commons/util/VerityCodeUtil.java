package com.immdream.commons.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码生成工具类
 * <p>
 * news com.immdream.commons.util
 *
 * @author immDream
 * @date 2023/04/13/17:09
 * @since 1.8
 */
public class VerityCodeUtil {
    //随机函数
    private static Random random = new Random();

    //验证码数据列表
    private static final char[] codeList = {
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    /**
     * 生成验证码并绘制验证码图片
     * @param stringNum 随机产生字符数量
     * @return 验证码以及图片地址
     */
    public static Map<String,Object> getCode(int stringNum) {
        int width = 100;//图片宽
        int height = 38;//图片高
        int lineSize = 40;//干扰线数量

        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);

        //产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));

        //绘制干扰线
        for(int i=0; i<= lineSize; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(13);
            int yl = random.nextInt(15);
            g.drawLine(x, y, x+xl, y+yl);
        }

        //绘制随机字符
        StringBuilder randomString = new StringBuilder();
        for(int i = 1;i <= stringNum;i++){
            //设置文字
            g.setFont(new Font("Console", Font.CENTER_BASELINE,20));
            //设置颜色
            g.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
            //设置字符
            String str = String.valueOf(codeList[random.nextInt(codeList.length)]);
            //记录字符
            randomString.append(str);
            //绘制偏移
            g.translate(random.nextInt(3), random.nextInt(5));
            //绘制坐标
            g.drawString(str, 5 + 13 * (i - 1), 16);
        }
        g.dispose();

        Map<String,Object> result = new HashMap<>();
        result.put("code",randomString.toString());
        result.put("io",image);
        return result;
    }
}
