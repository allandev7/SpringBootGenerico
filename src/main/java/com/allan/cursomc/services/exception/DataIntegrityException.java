package com.allan.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		//mostra mensagem de erro
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		//mensagem de erro e causa anterior
		super(msg, cause);
	}

}
