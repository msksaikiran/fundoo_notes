package com.bridgelabz.fundoonotesBackend.utility;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotesBackend.entity.User;

@Component
public class MailService2 {
	
	
	public void senEmailMail(User user,JavaMailSenderImpl mailSender,String token) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject("Registration conformation.");
		message.setText("Hello "+ user.getName()+" link to verify the User:\n"+"http://localhost:4200/resetPassword/"+token);
	    mailSender.send(message);
	}
}
