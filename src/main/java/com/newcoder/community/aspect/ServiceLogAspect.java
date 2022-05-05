package com.newcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/4/30 18:57
 */
@Component
@Aspect
public class ServiceLogAspect {
    private Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);
    //固定写法，里面的execution有作用区别范围
    @Pointcut("execution(* com.newcoder.community.service.*.*(..))")
    public void pointCut(){

    }
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        //用户记录日志增加
        ServletRequestAttributes attributes  = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes==null){
            return;
        }  //防止为空
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();   //获得登录IP
        String now  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        //获取我们将要得到的方法
        String target = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();

        logger.info(String.format("用户[%s],在[%s],访问了[%s].",ip,now,target));
    }
}
