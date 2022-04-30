package com.newcoder.community;

import com.newcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 10:31
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionTest {
    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave1(){
        Object object = alphaService.save1();
        System.out.println(object);
    }

    @Test
    public void testSave2(){
        Object object = alphaService.save2();
        System.out.println(object);
    }
}
