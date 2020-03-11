package com.bridgelabz.fundoonote.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {

	private String Message;
	private int StatusCode;
	//private Object user;
	
	public String getMessage() {
		return Message;
	}
	public Response(String message, int statusCode) {
		super();
		Message = message;
		StatusCode = statusCode;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public int getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}
	
	
}
