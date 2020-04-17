package com.bridgelabz.fundoonote.utility;

import java.io.Serializable;

import org.springframework.amqp.AmqpException;

public class Email implements Serializable{
	
	
//	public Email() {
//		super();
//	}

	private String emailId;
	private String token;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}