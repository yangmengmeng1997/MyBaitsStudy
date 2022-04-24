package com.newcoder.community;

import com.newcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 12:58
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void TestTextMail(){
        mailClient.sendMail("13130992080@qq.com","Test","Welcome");
    }

    @Test
    public void TestHtmlMail(){
        Context context = new Context();
        context.setVariable("username","小染");

        String mycontext = templateEngine.process("/mail/demo",context);
        mailClient.sendMail("13130992080@qq.com","HTMLTest",mycontext);
    }
}
