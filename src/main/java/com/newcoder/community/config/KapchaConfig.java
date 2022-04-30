package com.newcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author xiuxiaoran
 * @date 2022/4/24 22:41
 * 使用别人写好的类例来实现验证码的生成
 */
@Configuration   //声明为配置类
public class KapchaConfig {
    @Bean   //表示这个类可以被框架管理
    public Producer KaptchaProducer(){   //实例化这个接口
        Properties properties = new Properties();
        //配置属性值
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.image.height","40");
        properties.setProperty("kaptcha.textproducer.front.size","32");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        DefaultKaptcha kaptcha = new DefaultKaptcha();   //继承接口的实现类
        Config config = new Config(properties);  //配置类
        kaptcha.setConfig(config);
        return kaptcha;  //返回子类的实现
    }
}
