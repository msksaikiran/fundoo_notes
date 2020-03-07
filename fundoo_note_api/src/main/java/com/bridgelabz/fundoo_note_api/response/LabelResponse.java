package com.bridgelabz.fundoo_note_api.response;

import com.bridgelabz.fundoo_note_api.entity.Label;

public class LabelResponse {
	
	private String Message;
	private Label labelDetails;
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

	public Label getLabelDetails() {
		return labelDetails;
	}

	public void setLabelDetails(Label labelDetails) {
		this.labelDetails = labelDetails;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
