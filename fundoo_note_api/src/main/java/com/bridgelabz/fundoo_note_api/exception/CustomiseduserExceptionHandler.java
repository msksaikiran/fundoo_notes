package com.bridgelabz.fundoo_note_api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoo_note_api.response.ExceptionResponse;

@ControllerAdvice
public class CustomiseduserExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserException(UserException ex) {
		
		
		return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getMessage()));
	
	}

}
