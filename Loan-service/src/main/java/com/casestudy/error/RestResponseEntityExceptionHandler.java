package com.casestudy.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.casestudy.models.ErrorMessage;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(LoanNotFoundException.class)
	public ResponseEntity<ErrorMessage> loanNotFoundException(LoanNotFoundException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(feign.FeignException.class)
	public ResponseEntity<ErrorMessage> feignException(feign.FeignException ex) {
		ErrorMessage message = new ErrorMessage();

		String status = ex.getMessage().split(" ")[0].substring(1, 4);


		if (status.equals("503")) {
			message.setMessage("Authorization Process Failed");
			message.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
		}

		else if (status.equals("403")) {
			message.setMessage("Invalid Token");
			message.setStatus(HttpStatus.FORBIDDEN);
		} else {
			message.setMessage("Something Went Wrong");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ErrorMessage>(message, message.getStatus());
//		System.out.println(ex.getMessage());
//		System.out.println(status);
//		Map<String, String> errorMap=new HashMap<>();
//		if(status.equals("503"))errorMap.put("ERROR", "Authorization process failed");
//		if(status.equals("403"))errorMap.put("ERROR", "invalid token");
//		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(LoanAmountLimitException.class)
	public ResponseEntity<ErrorMessage> loanAmountLimitException(LoanAmountLimitException exception){
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}
}
