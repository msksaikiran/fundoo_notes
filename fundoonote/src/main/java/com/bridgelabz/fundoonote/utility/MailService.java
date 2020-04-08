package com.bridgelabz.fundoonote.utility;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MailService implements IEmailService {
	
	
	private JavaMailSender javaMailSender;

	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Autowired
	RabbitMQSender rabbitsender;

	@Override
	//@RabbitListener(queues = "note.queue")
	public void send(Email user) {
		System.out.println("Sending mail to receiver");
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(user.getEmailId());
		message.setSubject("Registration conformation.");
		message.setText("Hello  link to verify the User:\n"+"http://localhost:8083/users/verify/"+user.getToken());
	    javaMailSender.send(message);

		System.out.println("email sent successfully");
	}

	

	
}