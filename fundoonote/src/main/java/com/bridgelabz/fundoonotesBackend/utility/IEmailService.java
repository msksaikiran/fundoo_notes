package com.bridgelabz.fundoonotesBackend.utility;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.bridgelabz.fundoonotesBackend.dto.Mail;
import com.bridgelabz.fundoonotesBackend.entity.User;

public interface IEmailService {
	
	void send(Email emailid);
	
}