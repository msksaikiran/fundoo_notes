package com.bridgelabz.fundoonotes.response;
import lombok.Data;

@Data

public class UserDetail {
	private String token;
	private int Message;
	private Object obj;

	public UserDetail(String token, int i, Object obj) {

		this.token = token;
		Message = i;
		this.obj = obj;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getMessage() {
		return Message;
	}

	public void setMessage(int message) {
		Message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
