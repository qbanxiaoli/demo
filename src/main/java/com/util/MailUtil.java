package com.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author qbanxiaoli
 * @description 邮件工具类
 * @create 2018/7/3 13:31
 */
@Slf4j
@Component
public class MailUtil {

    private static JavaMailSender javaMailSender;

    private static MailProperties mailProperties;

    public MailUtil(JavaMailSender javaMailSender, MailProperties mailProperties) {
        MailUtil.javaMailSender = javaMailSender;
        MailUtil.mailProperties = mailProperties;
    }

    /**
     * @param receiver 邮件接收者
     * @param content  邮件内容
     * @author qbanxiaoli
     * @description 发送邮件
     */
    @SneakyThrows
    public static void sendMail(String title, String receiver, String content) {
        log.info("邮件标题：{}", title);
        log.info("邮件接收者：{}", receiver);
        log.info("邮件内容：{}", content);
        log.info("-------开始发送邮件-------");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(receiver);
        helper.setSubject(title);
        helper.setText(content, true);
        javaMailSender.send(message);
        log.info("-------发送邮件成功-------");
    }

}
