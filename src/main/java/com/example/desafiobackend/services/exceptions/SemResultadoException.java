package com.example.desafiobackend.services.exceptions;

public class SemResultadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  SemResultadoException(String msg) {
		
		super(msg);
	}
	public  SemResultadoException(String msg,Throwable cause) {
		super(msg,cause);
	}	
	

}
