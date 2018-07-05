package com.silita.utils;

import java.lang.instrument.Instrumentation;

/**
 * Created by dh on 2018/7/5.
 */
public class ObjectSize {
    private static volatile Instrumentation instru;

    public static void premain(String args, Instrumentation inst) {
        instru = inst;
    }

    public static Long getSizeOf(Object object) {
        if (instru == null) {
            throw new IllegalStateException("Instrumentation is null");
        }
        return instru.getObjectSize(object);
    }

    public static void main(String[] args) {
        String a = new String("123");
        System.out.println(ObjectSize.getSizeOf(a));
    }
}