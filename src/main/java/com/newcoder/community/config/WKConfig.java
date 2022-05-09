package com.newcoder.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 22:45
 */
@Configuration
public class WKConfig {
    private static final Logger logger = LoggerFactory.getLogger(WKConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init(){  //启动服务时调用一次就好了
        //创建图片目录
        File file = new File(wkImageStorage);
        if(!file.exists()){
            file.mkdir();
            logger.info("创建目录成功");
        }
    }
}
