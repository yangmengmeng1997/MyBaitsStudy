package com.newcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author xiuxiaoran
 * @date 2022/4/30 22:12
 * redis 的配置类
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);  //设置连接数据库的工厂

        //设置key的序列化发格式
         template.setKeySerializer(RedisSerializer.string());  //可以序列化字符串的
        //设置key的序列化格式
        template.setValueSerializer(RedisSerializer.json());  // Json 结构化数据
        //设置hash的key的序列化格式
        template.setHashKeySerializer(RedisSerializer.string());
        //设置hash的value的序列化格式
        template.setValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();  //出发设置生效

        return template;
    }
}
