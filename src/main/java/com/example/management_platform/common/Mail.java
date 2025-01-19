package com.example.management_platform.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Mail {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMail(String code,String touser) {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("邮箱注册码");
        // 设置邮件发送者，这个跟application.yml中设置的要一致     username:
        message.setFrom("2248384953@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10@qq.com","12qq.com");
        message.setTo(touser);
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("邮箱注册码：" + code);
        // 发送邮件
        javaMailSender.send(message);
    }
}
