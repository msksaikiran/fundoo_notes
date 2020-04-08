package com.bridgelabz.fundoonote.utility;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class RabbitMQSender {
	
	@Autowired
	IEmailService mailservice;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${fundoo.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Object obj) {
		System.out.println(exchange + "    " + routingkey);
		System.out.println("Send msg = " + obj.toString());
		rabbitTemplate.convertAndSend(exchange, routingkey, obj);
	}
	
	public void Reciver(Object object) {
		System.out.println("entered to listner of rabbit mq");
		
		mailservice.send((Email) object);
		
	}
}