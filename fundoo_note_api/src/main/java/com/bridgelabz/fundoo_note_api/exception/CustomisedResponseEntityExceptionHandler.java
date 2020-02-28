package com.bridgelabz.fundoo_note_api.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoo_note_api.response.ExceptionResponse;


@ControllerAdvice
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
