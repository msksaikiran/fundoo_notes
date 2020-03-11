package com.bridgelabz.fundoonote.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonote.response.ExceptionResponse;

@ControllerAdvice
public class CustomisedNoteExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoteException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserException(NoteException ex) {
		
		
		return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionResponse(LocalDateTime.now(),ex.getMessage()));
	
	}

}