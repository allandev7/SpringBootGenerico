package com.allan.cursomc.services.exception;

public class AuthorizationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String msg) {
		//mostra mensagem de erro
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		//mensagem de erro e causa anterior
		super(msg, cause);
	}

}
