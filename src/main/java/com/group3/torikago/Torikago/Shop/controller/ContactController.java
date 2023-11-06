package com.group3.torikago.Torikago.Shop.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
    @Autowired
    private JavaMailSender mailSender;

    public ContactController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/contact")
    public String showContact(){
        return "contact-form";
    }
    @PostMapping("/contact")
    public String submitContact(HttpServletRequest request)  {
        String fullname= request.getParameter("fullname");
        String email= request.getParameter("email");
        String subject= request.getParameter("subject");
        String content= request.getParameter("content");


       SimpleMailMessage message = new SimpleMailMessage();

//        MimeMessage message=javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);


        message.setFrom("ddinhhoang29@gmail.com");
        message.setTo("nar9591@gmail.com");

        String mailSubject= fullname+"has sent a message";
        String mailContent="Sender name: "+fullname+"\n";
        mailContent+="Sender Email: "+email+"\n";
        mailContent+="Subject: "+subject+"\n";
        mailContent+="Content: "+content+"\n";



        message.setSubject(mailSubject);
        message.setText(mailContent);

        mailSender.send(message);
        return "mail-message";
    }
}
