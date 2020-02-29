package com.bridgelabz.fundoo_note_api.response;

public class NoteResponse {
	private String Message;
	private Object obj;
	
	public NoteResponse(String message, Object obj) {
		super();
		Message = message;
		this.obj = obj;
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
