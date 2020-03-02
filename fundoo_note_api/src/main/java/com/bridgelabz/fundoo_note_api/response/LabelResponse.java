package com.bridgelabz.fundoo_note_api.response;

import com.bridgelabz.fundoo_note_api.entity.Label;

public class LabelResponse {
	private String Message;
	private Label notes;
	private Object result;

	

	public LabelResponse(String message, Object result) {
		super();
		Message = message;
		this.result = result;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Label getNotes() {
		return notes;
	}

	public void setNotes(Label notes) {
		this.notes = notes;
	}

}
