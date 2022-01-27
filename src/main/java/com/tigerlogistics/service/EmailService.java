package com.tigerlogistics.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tigerlogistics.email.EmailSender;

@Service
public class EmailService implements EmailSender {

	private static final Logger logger= LoggerFactory.getLogger(EmailService.class);
	
	
	
	
	@Override
    @Async
    public void send(String to, String email) {
		
		// Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
		
        try {
        	
        	JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        	mailSender.setJavaMailProperties(properties);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            //helper.setFrom("akhilmurali662@gmail.com");
            helper.setFrom("manoj.rgv@gmail.com");
//            mailSender.setUsername("akhilmurali662@gmail.com");
            mailSender.setUsername("manoj.rgv@gmail.com");
            
            mailSender.setPassword("mlwxsufwtznesoqg");
//            mailSender.setPassword("aufsvelxmxbsqkmx");
            
            mailSender.send(mimeMessage);
        } 
        
        catch (MessagingException e) {
            logger.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

	
}
