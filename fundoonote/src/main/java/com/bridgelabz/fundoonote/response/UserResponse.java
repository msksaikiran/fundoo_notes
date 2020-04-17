package com.bridgelabz.fundoonote.response;

import javax.validation.Valid;

import com.bridgelabz.fundoonote.dto.UserLogin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
	private String Message;
	private String token;
	private Object obj;
	
	

	public UserResponse(String message, String token) {
		super();
		this.token = token;
		Message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public UserResponse(String token, String message, Object obj) {
		super();
		this.token = token;
		Message = message;
		this.obj = obj;
	}

	

	
	

}
