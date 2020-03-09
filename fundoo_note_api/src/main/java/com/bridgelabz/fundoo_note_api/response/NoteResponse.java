package com.bridgelabz.fundoo_note_api.response;

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
