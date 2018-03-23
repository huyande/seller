package com.netease.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-21.
 */

@Aspect
@Component
public class DebugLoggerAspect {
    private static final Logger logger = LoggerFactory.getLogger(DebugLoggerAspect.class);

    @Before("execution(* com.netease.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null) {
                sb.append("arg:" + arg.toString() + "|");
            }
        }
        logger.info("before method:["+joinPoint.getSignature()+"]" + sb.toString());
    }

    @After("execution(* com.netease.controller.IndexController.*(..))")
    public void afterMethod() {
        logger.info("after method" + new Date());
    }
}

