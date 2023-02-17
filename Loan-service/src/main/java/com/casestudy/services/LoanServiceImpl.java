package com.casestudy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.entities.Loan;
import com.casestudy.repositories.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService{
	@Autowired
	LoanRepository loanRepository;
	@Override
	public List<Loan> getLoansDetails() {
		
		return loanRepository.findAll();
	}

}
