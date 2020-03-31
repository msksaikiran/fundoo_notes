package com.bridgelabz.fundoonote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class NoteResponse {
	private String Message;
	private Object notes;
	private int statusCode;
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Object getNotes() {
		return notes;
	}
	public void setNotes(Object notes) {
		this.notes = notes;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public NoteResponse(String message, Object notes, int statusCode) {
		super();
		Message = message;
		this.notes = notes;
		this.statusCode = statusCode;
	}
	
	

}
