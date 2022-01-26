package com.tigerlogistics.service;

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
        try {
        	
        	JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("manoj.rgv@gmail.com");
            mailSender.send(mimeMessage);
        } 
        
        catch (MessagingException e) {
            logger.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

	
}
