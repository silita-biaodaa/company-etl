package com.silita.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author:chenzhiqiang
 * @Date:2018/6/27 14:12
 * @Description:记录业务类日志的切面
 */
@Aspect
@Component
public class ServiceLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.silita.service.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object pointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        Object object = joinPoint.proceed(args);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String allName = signature.getDeclaringTypeName() + "." + signature.getName();
        long end = System.currentTimeMillis();
        long took = end - start;
        if (took > 3000) {
            logger.info(String.format("%s 执行耗时：%sms", allName, took));
        }
        //注意：这里一定要有返回值，不返回的话，调用的业务方法返回值会为null
        return object;
    }
}
