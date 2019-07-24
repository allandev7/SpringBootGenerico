package com.allan.cursomc.services.exception;

public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		//mostra mensagem de erro
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		//mensagem de erro e causa anterior
		super(msg, cause);
	}

}
