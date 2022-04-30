package com.newcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiuxiaoran
 * @date 2022/4/26 16:01
 * 自定义注解，盲区
 */
@Target(ElementType.METHOD)   //注解定义在方法上面
@Retention(RetentionPolicy.RUNTIME) //程序运行时有效
public @interface LoginRequired {

}
