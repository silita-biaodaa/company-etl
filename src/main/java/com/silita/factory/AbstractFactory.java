package com.silita.factory;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/5 21:29
 * @Description: 抽象工厂
 */
public abstract class AbstractFactory {
    /**
     * 数据加工
     *
     * @param object
     */
    public abstract void process(Object object);
}
