package com.bridgelabz.fundoonote.response;

import com.bridgelabz.fundoonote.entity.Label;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LabelResponse {
	
	private String Message;
	private int statusCode;
	private Object result;

	
}
