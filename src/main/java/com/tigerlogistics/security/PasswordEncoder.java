package com.tigerlogistics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tigerlogistics.email.EmailValidator;

@Configuration
public class PasswordEncoder {

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }
	
}
