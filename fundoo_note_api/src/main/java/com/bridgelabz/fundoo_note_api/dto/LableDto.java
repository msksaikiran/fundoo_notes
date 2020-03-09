package com.bridgelabz.fundoo_note_api.dto;

import lombok.NonNull;

public class LableDto {

	@NonNull
	private String LName;

	public String getLName() {
		return LName;
	}

	public void setLName(String lName) {
		LName = lName;
	}

}
