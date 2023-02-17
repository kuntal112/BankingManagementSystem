package com.banking.error;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.banking.models.ErrorMessage;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception,WebRequest request) {
		ErrorMessage message=new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
		return new ResponseEntity<ErrorMessage>(message,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(BadCredentialException exception,WebRequest request) {
		ErrorMessage message=new ErrorMessage(HttpStatus.UNAUTHORIZED,exception.getMessage());
		return new ResponseEntity<ErrorMessage>(message,HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String,String>> constraintViolationException(ConstraintViolationException ex){
		Map<String, String> errorMap = new HashMap<>();
		ex.getConstraintViolations().forEach(x->errorMap.put(x.getPropertyPath().toString(),x.getMessageTemplate()));
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Map<String,String>> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
		Map<String, String> errorMap = new HashMap<>();
//		Duplicate entry '1234567893' for key 'U_contactNum'
//		Duplicate entry 'ESDFD4442C' for key 'U_pan'
//		if(ex.getMessage().contains(""))
		System.out.println((ex.getMessage()).getClass()); 
		String[] split = ex.getMessage().split(" ");
		String str=split[5]+" "+split[2]+" already exist";
		errorMap.put("message", str);
		System.out.println("called sqlintegrity");
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
}
