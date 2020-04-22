package com.bridgelabz.fundoonotesBackend.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
public class LabelException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private HttpStatus statusCode;
	private String statusMessage;
	
	public  LabelException(HttpStatus statusCode, String message) {
		super(message);
		this.statusCode=statusCode;
		this.statusMessage=message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	
}