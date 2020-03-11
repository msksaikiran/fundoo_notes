package com.bridgelabz.fundoonote.dto;

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
