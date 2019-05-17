package com.silita.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/17 9:44
 * @Description: 日期时间工具类
 */
public class DateTimeUtils {
    private static final Random random = new Random();

    public static final String format1 = "yyyy-MM-dd HH:mm:ss";

    public static final String format2 = "yyyyMMddHHmmss";

    /**
     * 获得当前日期时间
     *
     * @return
     */
    public static String current() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern(format1));
    }

    /**
     * 获得当前日期时间
     *
     * @return
     */
    public static String current(String format) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获得随机的线程休眠时间
     *
     * @return
     */
    public static int getRandomSleep() {
        int sleep = 100 + (random.nextInt(5)) * 100;
        return sleep;
    }

    /**
     * 获得随机的线程休眠时间
     *
     * @param seconds 休眠间隔（单位：秒，返回：毫秒）
     * @return
     */
    public static int getRandomSleep(int seconds) {
        int sleep = 1 + random.nextInt(seconds);
        return sleep * 1000;
    }

    /**
     * 获得固定的线程休眠时间
     *
     * @return
     */
    public static int getFixedSleep() {
        return 0;
    }
}
