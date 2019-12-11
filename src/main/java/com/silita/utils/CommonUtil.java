package com.silita.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 - 公用
 */

public class CommonUtil {

    /**
     * 随机获取UUID字符串(无中划线)
     *
     * @return UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        StringBuffer id = new StringBuffer("");
        id.append(uuid.substring(0, 8)).append(uuid.substring(9, 13)).append(uuid.substring(14, 18)).append(uuid.substring(19, 23)).append(uuid.substring(24));
        return id.toString().trim();
    }

    /**
     * 获取总页数
     *
     * @param total
     * @param pageSize
     * @return
     */
    public static Integer getPages(Integer total, Integer pageSize) {
        Integer pages = 0;
        if (total % pageSize == 0) {
            pages = total / pageSize;
        } else {
            pages = (total / pageSize) + 1;
        }
        return pages;
    }

    /**
     * 解析数字
     * @param salce
     * @return
     */
    public static String analysisRegx(String salce){
        String pattenShuzi = "[0-9]{1,}[.][0-9]{1,}|[0-9]{1,}+";
        Pattern r1 = Pattern.compile(pattenShuzi);
        Matcher m1 = r1.matcher(salce);
        if (m1.find()){
            return m1.group();
        }
        return null;
    }
}