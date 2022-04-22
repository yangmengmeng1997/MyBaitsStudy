package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 10:14
 */

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TestLogger {
    private static final Logger logger= LoggerFactory.getLogger(TestLogger.class);

    @Test
    public void test1(){
        logger.debug("debuglogger");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
