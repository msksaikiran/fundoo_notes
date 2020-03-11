package com.bridgelabz.fundoonote.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonote.response.ExceptionResponse;

@ControllerAdvice
public class CustomisedLabelExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(LabelException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserException(LabelException ex) {
		
		
		return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getMessage()));
	
	}

}