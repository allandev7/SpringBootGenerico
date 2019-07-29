package com.allan.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError{
	
	private List<FieldMessage> errors  = new ArrayList<FieldMessage>();

	public ValidationError(Long timestamp, Integer status, String error, String message, 
			String path) {
		super(timestamp, status, error, message, path);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
	
	

}
