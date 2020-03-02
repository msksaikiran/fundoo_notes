package com.bridgelabz.fundoo_note_api.response;

public class NoteResponse {
	private String Message;
	private Object notes;

	public NoteResponse(String message, Object obj) {
		super();
		Message = message;
		this.notes = obj;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Object getObj() {
		return notes;
	}

	public void setObj(Object obj) {
		this.notes = obj;
	}

}
