package com.airbnb.util;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String sub, String msg) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(to);
        sm.setSubject(sub);
        sm.setText(msg);
        javaMailSender.send(sm);
    }

    public void sendEmailWithAttachment(String to, String sub, String msg, String pathToAttachment) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(sub);
        helper.setText(msg);

        File file = new File(pathToAttachment);
        helper.addAttachment(file.getName(), file);

        javaMailSender.send(message);
    }
}
