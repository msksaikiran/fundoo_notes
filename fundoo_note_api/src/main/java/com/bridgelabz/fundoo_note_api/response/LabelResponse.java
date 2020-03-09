package com.bridgelabz.fundoo_note_api.response;

public class LabelResponse {
	
	private String Message;
	//private Label labelDetails;
	//private Object result;


	public LabelResponse(String message) {
		super();
		Message = message;
		//this.result = result;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
