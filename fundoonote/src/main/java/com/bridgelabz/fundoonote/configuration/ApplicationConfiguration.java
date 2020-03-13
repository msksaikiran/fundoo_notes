package com.bridgelabz.fundoonote.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bridgelabz.fundoonote.utility.MailService;

@Configuration
public class ApplicationConfiguration {
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSenderImpl mail() {

		return new JavaMailSenderImpl();

	}
}
