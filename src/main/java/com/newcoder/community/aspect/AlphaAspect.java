package com.newcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author xiuxiaoran
 * @date 2022/4/30 17:28
 * 将上面的注解结束后,AOP的作用失效
 */
//@Component
//@Aspect
public class AlphaAspect {
    //定义切点
    /*
        execution 固定的关键字 ； *表示任意返回值
        service.*.* 下面的所有service包下面的类的所有方法
        (..) 所有的参数
     */
    @Pointcut("execution(* com.newcoder.community.service.*.*(..))")
    public void pointCut(){

    }

    //定义通知 ,表示在这个切点前有效
    @Before("pointCut()")
    public void before(){
        System.out.println("before execution");   //2
    }

    @After("pointCut()")
    public void after(){
        System.out.println("after finish");       //4
    }

    @AfterReturning("pointCut()")
    public void afterReturn(){
        System.out.println("after returnning");  //3
    }

    @AfterThrowing("pointCut()")
    public void afterExcep(){
        System.out.println("after Except");    //遇到错误时调用
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");     //1
        Object proceed = joinPoint.proceed();
        System.out.println("around after");      //5
        return proceed;  //返回代理对象
    }
}
