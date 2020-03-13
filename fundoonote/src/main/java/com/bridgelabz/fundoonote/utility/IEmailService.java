package com.bridgelabz.fundoonote.utility;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.bridgelabz.fundoonote.entity.User;

public interface IEmailService {
	
	void senMail(User user,JavaMailSenderImpl mailSender,String token);
	String getlink(String link,String id);

}