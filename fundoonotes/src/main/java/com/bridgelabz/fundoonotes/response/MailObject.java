package com.bridgelabz.fundoonotes.response;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class MailObject  implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;

private String Email;
private String Message;
private String Subject;
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getMessage() {
	return Message;
}
public void setMessage(String message) {
	Message = message;
}
public String getSubject() {
	return Subject;
}
public void setSubject(String subject) {
	Subject = subject;
}

}
