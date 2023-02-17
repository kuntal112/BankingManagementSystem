package com.casestudy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.entities.Loan;
import com.casestudy.entities.LoanTransaction;
import com.casestudy.error.LoanAmountLimitException;
import com.casestudy.error.LoanNotFoundException;
import com.casestudy.repositories.LoanRepository;
import com.casestudy.repositories.LoanTransactionRepository;

@Service
public class LoanTransactionServiceImpl implements LoanTransactionService{
	@Autowired
	LoanRepository loanRepository;
	@Autowired
	LoanTransactionRepository transactionRepository;
	@Override
	public LoanTransaction saveTransaction(LoanTransaction loanTransaction) throws LoanNotFoundException, LoanAmountLimitException {
		// TODO Auto-generated method stub
		Long loan_id = loanTransaction.getLoan().getLoan_id();
		Optional<Loan> findById = loanRepository.findById(loan_id);
		findById.orElseThrow(()-> new LoanNotFoundException("No Such Loan Exist"));
		if(findById.get().getMax_loan_amount().compareTo(loanTransaction.getLoan_amount())<0 ) {
			throw new LoanAmountLimitException("You are allowed to avail a loan upto: "+findById.get().getMax_loan_amount());
		}
		return transactionRepository.save(loanTransaction);
	}
	@Override
	public List<LoanTransaction> getTransactionsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return transactionRepository.findLoanTransactionByUserId(userId);
	}

}
