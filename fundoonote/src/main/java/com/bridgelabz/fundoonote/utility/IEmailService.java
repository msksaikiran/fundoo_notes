package com.bridgelabz.fundoonote.utility;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.bridgelabz.fundoonote.entity.User;

public interface IEmailService {
	
	void send(Email emailid);
	
}