package com.bridgelabz.fundoonotesBackend.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.bridgelabz.fundoonotesBackend.dto.Mail;
import com.bridgelabz.fundoonotesBackend.entity.User;
import com.bridgelabz.fundoonotesBackend.utility.Email;

public interface IEmailService {
	
	void send(Email emailid);
	
}