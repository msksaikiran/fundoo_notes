package com.bridgelabz.fundoo_note_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String statusMessage;
	
	public  UserException(String message) {
		super(message);
	}
		
}
