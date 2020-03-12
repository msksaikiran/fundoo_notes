package com.bridgelabz.fundoonote.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class RabbitMqConfig {
//	@Value("${fundoo.rabbitmq.queue}")
//	String queueName;
//
//	@Value("${fundoo.rabbitmq.exchange}")
//	String exchange;
//
//	@Value("${fundoo.rabbitmq.exchange_key}")
//	String routingkey;
//
//	@Bean
//	Queue queue() {
//		return new Queue(queueName, false);
//	}
//
//	@Bean
//	DirectExchange exchange() {
//		return new DirectExchange(exchange);
//	}
//
//	@Bean
//	Binding binding(Queue queue, DirectExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
//	}
//
//	@Bean
//	public MessageConverter jsonMessageConverter() {
//		return new Jackson2JsonMessageConverter();
//	}
//}
