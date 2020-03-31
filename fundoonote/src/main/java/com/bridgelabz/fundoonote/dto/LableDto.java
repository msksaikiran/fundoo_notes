package com.bridgelabz.fundoonote.dto;

import lombok.Data;

@Data
public class LableDto {

	//@NonNull
	private String lableName;

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	
}
