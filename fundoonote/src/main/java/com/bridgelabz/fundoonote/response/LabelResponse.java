package com.bridgelabz.fundoonote.response;

import com.bridgelabz.fundoonote.entity.Label;

public class LabelResponse {
	
	private String Message;
	//private Label labelDetails;
	private Label result;


	

	public LabelResponse(String message) {
		super();
		Message = message;
	}

	public LabelResponse(String message, Label result) {
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

	public Label getResult() {
		return result;
	}

	public void setResult(Label result) {
		this.result = result;
	}
	
	
}
