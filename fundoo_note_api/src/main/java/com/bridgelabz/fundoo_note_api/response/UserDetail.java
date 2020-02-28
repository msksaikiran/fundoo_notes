package com.bridgelabz.fundoo_note_api.response;
import lombok.Data;

@Data

public class UserDetail {
	private String token;
	private String Message;
	private Object obj;

	public UserDetail(String token, String i, Object obj) {

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

}
