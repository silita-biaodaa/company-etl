package com.silita.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/27 9:28
 * @Description:字段变更记录注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldChangeRecord {
    /**
     * 表名
     *
     * @return
     */
    public String table() default "";

    /**
     * 表中id
     *
     * @return
     */
    public String id();

    /**
     * 需要记录变更的字段名列表
     *
     * @return
     */
    public String[] fields();
}
