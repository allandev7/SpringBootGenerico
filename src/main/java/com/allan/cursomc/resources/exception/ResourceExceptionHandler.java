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
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.pagamento.resources.exception.StandardError;

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
			ValidationError err = new ValidationError(System.currentTimeMillis(),HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), req.getRequestURI());
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
		
		@ExceptionHandler(AmazonServiceException.class)
		public ResponseEntity<StandarError> amazonService(AmazonServiceException e, HttpServletRequest request) {
			HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
			StandarError err = new StandarError(System.currentTimeMillis(), code.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI()); 
			return ResponseEntity.status(code).body(err);
		}

		@ExceptionHandler(AmazonClientException.class)
		public ResponseEntity<StandarError> amazonClient(AmazonClientException e, HttpServletRequest request) {
			StandarError err = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Cliente", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}

		@ExceptionHandler(AmazonS3Exception.class)
		public ResponseEntity<StandarError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
			StandarError err = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro AmazonS3", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
}
