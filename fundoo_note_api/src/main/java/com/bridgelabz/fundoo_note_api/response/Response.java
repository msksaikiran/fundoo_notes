package com.bridgelabz.fundoo_note_api.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {

	private String Message;
	//private HttpStatus StatusCode;
	//private Object user;
}
