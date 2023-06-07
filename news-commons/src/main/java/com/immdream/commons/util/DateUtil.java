package com.immdream.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * <p>
 * news com.immdream.commons.util
 *
 * @author immDream
 * @date 2023/05/01/11:19
 * @since 1.8
 */
public class DateUtil {
    /**
     * 工具类－生成日期作为图片文件名称
     * @return
     */
    public static String getDateFileName(){
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(dt);
    }

    /**
     * 获取时间
     * @return
     */
    public static String getDate(){
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }
}
