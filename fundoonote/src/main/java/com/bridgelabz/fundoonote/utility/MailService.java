package com.bridgelabz.fundoonote.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonote.entity.User;
//
//
@Component
public class MailService {
	
//	@Autowired 
//	private static  RabbitMQSender rabbitSender;

	@Autowired
	private JwtGenerator generate;
		
	//@RabbitListener(queues = "note.queue")
	public void senMail(User user,JavaMailSenderImpl mailSender,String token) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject("Registration conformation.");
		message.setText("Hello "+ user.getName()+" link to verify the User:\n"+"http://localhost:8083/users/verify/"+token);
	    mailSender.send(message);
		//rabbitSender.send(user);
	}
}
//
//
//	
//
//	@Override
//	public String getlink(String link, String id) {
//		return link + generate.jwtToken(Long.parseLong(id));
//	}


//}
