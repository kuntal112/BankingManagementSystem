package com.casestudy.services;

import java.util.List;

import com.casestudy.entities.LoanTransaction;
import com.casestudy.error.LoanAmountLimitException;
import com.casestudy.error.LoanNotFoundException;

public interface LoanTransactionService {
	LoanTransaction saveTransaction(LoanTransaction loanTransaction) throws LoanNotFoundException, LoanAmountLimitException;
	List<LoanTransaction> getTransactionsByUserId(Long userId);
}
