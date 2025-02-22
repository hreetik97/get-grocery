package com.grocery_booking_api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.grocery_booking_api.controller.*.*(..)) || execution(* com.grocery_booking_api.service.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.grocery_booking_api.controller.*.*(..)) || execution(* com.grocery_booking_api.service.*.*(..))", returning = "result")
    public void logAfterMethodReturn(JoinPoint joinPoint, Object result) {
        log.info("Method {} executed successfully with result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.grocery_booking_api.controller.*.*(..)) || execution(* com.grocery_booking_api.service.*.*(..))", throwing = "exception")
    public void logAfterMethodThrows(JoinPoint joinPoint, Throwable exception) {
        log.error("Method {} threw exception: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}