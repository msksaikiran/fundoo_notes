package com.bridgelabz.fundoonotesBackend.response;

import com.bridgelabz.fundoonotesBackend.entity.Label;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
public class LabelResponse {
	
	private String Message;
	private int statusCode;
	private Object result;
	public LabelResponse(String message, int statusCode, Object result) {
		super();
		Message = message;
		this.statusCode = statusCode;
		this.result = result;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	
}
