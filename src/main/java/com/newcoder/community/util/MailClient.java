package com.newcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 12:48
 */

@Component  //通用的注解可以供所有层次的bean使用
public class MailClient {
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender  mailSender ;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject , String context) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper  helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(context,true);  //支持HTML文本传输
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败"+e.getMessage());
        }
    }
}
