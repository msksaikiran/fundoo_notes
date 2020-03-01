package com.bridgelabz.fundoo_note_api.response;

import java.util.List;

import com.bridgelabz.fundoo_note_api.entity.Label;

public class LabelResponse {
	private String Message;
	private Label notes;
    private List<Label> result;
    
	public LabelResponse(String message, Label result) {
		super();
		Message = message;
		this.notes = result;
	}


	public LabelResponse(String message, List<Label> result) {
		this.Message=message;
		this.result=result;
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
