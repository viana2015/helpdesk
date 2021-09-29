package com.jrcg.helpdesk.resouces.excepetion;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jrcg.helpdesk.sevices.exceptions.DataIntegrityViolationException;
import com.jrcg.helpdesk.sevices.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExeceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardErro> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
		
		StandardErro error = new StandardErro(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Object Not Found", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardErro> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {
		
		StandardErro error = new StandardErro(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Violação de dados", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErro> methodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		
		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Validation error ", "Erro na validação dos campos ", request.getRequestURI());
		
		for (FieldError x : ex.getFieldErrors()) {
			errors.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		
	}
}
