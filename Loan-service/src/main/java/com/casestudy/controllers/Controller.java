package com.casestudy.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.entities.Loan;
import com.casestudy.entities.LoanTransaction;
import com.casestudy.models.AuthResponse;
import com.casestudy.services.AuthorizationService;
import com.casestudy.services.LoanService;
import com.casestudy.services.LoanTransactionService;

@RestController
@RequestMapping("/loan")
public class Controller {
	
	Logger logger = LoggerFactory.getLogger("loan-Controller-Logger");
	
	@Autowired
	AuthorizationService authorizationService;
	@Autowired
	LoanService loanService;
	@Autowired
	LoanTransactionService transactionService;
	
	
	@GetMapping("/loan_types")
	public ResponseEntity<List<Loan>> getLoansDetails(@RequestHeader("Authorization") String jwt) throws Exception{
		if(!(jwt.length()>0 && authorizationService.validateJwt(jwt).isValid())) {	
			logger.error("Failed to validate the JWT :: " + jwt);
			throw new Exception("invalid request");
		}
		List<Loan> loansDetails = loanService.getLoansDetails();
		return new ResponseEntity<List<Loan>>(loansDetails,HttpStatus.OK);
	}
	
	
	@PostMapping("/applyloan")
	public ResponseEntity<String> saveLoanTransaction(@RequestHeader("Authorization") String jwt,@RequestBody LoanTransaction loanTransaction) throws Exception{
		AuthResponse validateJwt = authorizationService.validateJwt(jwt);
		if(!(jwt.length()>0 && validateJwt.isValid())) {	
			logger.error("Failed to validate the JWT :: " + jwt);
			throw new Exception("invalid request");
		}
		loanTransaction.setUserId(validateJwt.getId());
		
		LoanTransaction saveTransaction = transactionService.saveTransaction(loanTransaction);
		return new ResponseEntity<String>("Applied Successfully",HttpStatus.CREATED);
	}
	
	
	@GetMapping("/applied_loans")
	public ResponseEntity<List<LoanTransaction>> getTransaction(@RequestHeader("Authorization") String jwt) throws Exception{
		AuthResponse validateJwt = authorizationService.validateJwt(jwt);
		if(!(jwt.length()>0 && validateJwt.isValid())) {	
			logger.error("Failed to validate the JWT :: " + jwt);
			throw new Exception("invalid request");
		}
		List<LoanTransaction> transactionsById = transactionService.getTransactionsByUserId(validateJwt.getId());
		return new ResponseEntity<List<LoanTransaction>>(transactionsById,HttpStatus.OK);
	}
}
