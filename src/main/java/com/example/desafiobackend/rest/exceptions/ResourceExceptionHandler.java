package com.example.desafiobackend.rest.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.desafiobackend.services.exceptions.DataIntegrityException;
import com.example.desafiobackend.services.exceptions.SemResultadoException;

@ControllerAdvice
public class ResourceExceptionHandler {


	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> DataIntegrityViolationException(DataIntegrityException e,
			HttpServletRequest resquest) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(SemResultadoException.class)
	public ResponseEntity<StandardError> NoResultException(SemResultadoException e,
			HttpServletRequest resquest) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
