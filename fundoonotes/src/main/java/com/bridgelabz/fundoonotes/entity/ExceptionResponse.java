package com.bridgelabz.fundoonotes.entity;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String detaiuls;

	public ExceptionResponse(Date timestamp, String message, String detaiuls) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.detaiuls = detaiuls;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetaiuls() {
		return detaiuls;
	}

	public void setDetaiuls(String detaiuls) {
		this.detaiuls = detaiuls;
	}

}
