package com.allan.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.allan.cursomc.services.exception.AuthorizationException;
import com.allan.cursomc.services.exception.DataIntegrityException;
import com.allan.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
		@ExceptionHandler(ObjectNotFoundException.class)
		public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e, 
				HttpServletRequest req) {
			StandarError err = new StandarError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), req.getRequestURI());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		}
		
		@ExceptionHandler(DataIntegrityException.class)
		public ResponseEntity<StandarError> dataIntegrity(DataIntegrityException e, HttpServletRequest req) {
			StandarError err = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), req.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
		
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<StandarError> validation(MethodArgumentNotValidException e, HttpServletRequest req) {
			ValidationError err = new ValidationError(System.currentTimeMillis(),HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", "Erro de validação", req.getRequestURI());
			for(FieldError x : e.getBindingResult().getFieldErrors()) {
				err.addError(x.getField(), x.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
		}
		
		@ExceptionHandler(AuthorizationException.class)
		public ResponseEntity<StandarError> authorization(AuthorizationException e, HttpServletRequest req) {
			StandarError err = new StandarError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso Negado", e.getMessage(), req.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}
}
