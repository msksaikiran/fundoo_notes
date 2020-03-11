package com.bridgelabz.fundoonote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
	private String token;
	private String Message;
	private Object obj;
	
	public UserResponse(String token, String message) {
		super();
		this.token = token;
		Message = message;
	}

	
	

}
