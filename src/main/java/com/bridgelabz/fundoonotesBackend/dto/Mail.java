package com.bridgelabz.fundoonotesBackend.dto;

public class Mail {

	private String to;
	private String subject;
	private String text;
	
	
//	public Mail(String to, String subject, String context) {
//		super();
//		this.to = to;
//		this.subject = subject;
//		this.context = context;
//	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
