package com.bridgelabz.fundoonote.utility;
//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonote.entity.User;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${javainuse.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(User user) {
		rabbitTemplate.convertAndSend(exchange, routingkey, user);
		System.out.println("Send msg = " + user);
	    
	}
}