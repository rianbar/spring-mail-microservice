package com.auth.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    @Autowired
    private JavaMailSender mailSender;

    public void sendMailToRecipient(String username, String mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Welcome " + username + " to my Spring Application!");
        message.setText("Hello, " + username + ". Welcome to at my Spring application test, this is the message that " +
                "you will receive when someone subscribe in my app.");
        mailSender.send(message);
    }
}
