package com.allan.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.allan.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException ex, 
												HttpServletRequest request){
													
		StandarError err =  new StandarError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), 
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
}
