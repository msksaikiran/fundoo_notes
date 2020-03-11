package com.bridgelabz.fundoonote.response;

public class NoteResponse {
	private String Message;
	//private Object notes;

	public NoteResponse(String message) {
		super();
		Message = message;
		//this.notes = notes;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}


}
