package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author xiuxiaoran
 * @date 2022/4/27 15:34
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void test1(){
       String text = "这里可以赌博，可以✿◕嫖◕✿娼◕✿，◕✿可以◕✿吸毒◕✿，可以◕✿开票◕✿，啊哈哈哈哈！！！";
       String s = sensitiveFilter.filter(text);
       System.out.println(s);
    }
}
