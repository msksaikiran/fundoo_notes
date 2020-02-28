package com.bridgelabz.fundoo_note_api.utility;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo_note_api.entity.User;

@Component
public class MailService {
		
	public static void senMail(User user,JavaMailSenderImpl mailSender,String token) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject("Registration conformation.");
		message.setText("Hello "+ user.getName()+" link to verify the User:\n"+"http://localhost:8083/verify/"+token);
	    mailSender.send(message);
	}
}
