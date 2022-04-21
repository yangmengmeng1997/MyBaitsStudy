package com.newcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author xiuxiaoran
 * @date 2022/4/19 8:54
 */
@Configuration //表示这是一个配置类，不是普通类
public class AlphaConfig {
    @Bean   //标识这个类是需要装配到Spring容器中的bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
