package com.casestudy.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.casestudy.entities.Loan;
import com.casestudy.entities.LoanTransaction;
import com.casestudy.error.LoanAmountLimitException;
import com.casestudy.error.LoanNotFoundException;
import com.casestudy.repositories.LoanRepository;
import com.casestudy.repositories.LoanTransactionRepository;
@SpringBootTest
class LoanTransactionServiceImplTest {
	@Autowired
	LoanTransactionService transactionService;
	@MockBean
	LoanTransactionRepository transactionRepository;
	@MockBean
	LoanRepository loanRepository;
	List<LoanTransaction> loanTransactions;
	@BeforeEach
	void setUp() throws Exception {
		String dateString="1997-11-06";
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		Date createdDate=formatter.parse(dateString);
		Loan loan1=Loan.builder()
				.loan_id(1L)
				.loan_type("personal")
				.max_duration(10)
				.max_loan_amount(new BigDecimal("3000000"))
				.interest_rate(12)
				.build();
		Loan loan2=Loan.builder()
				.loan_id(2L)
				.loan_type("house")
				.max_duration(10)
				.max_loan_amount(new BigDecimal("30000000"))
				.interest_rate(9)
				.build();
		Loan loan3=Loan.builder()
				.loan_id(3L)
				.loan_type("other")
				.max_duration(10)
				.max_loan_amount(new BigDecimal("30000"))
				.interest_rate(9)
				.build();
		List<Loan> loans=new ArrayList<>();
		loans.add(loan1);
		loans.add(loan2);
		LoanTransaction loanTransaction1=LoanTransaction.builder()
				.transactionId(1L)
				.loan_amount(new BigDecimal("400000"))
				.tenure(5)
				.userId(1L)
				.created_at(createdDate)
				.loan(loan1)
				.build();
		LoanTransaction loanTransaction2=LoanTransaction.builder()
				.transactionId(2L)
				.loan_amount(new BigDecimal("12000000"))
				.tenure(5)
				.userId(2L)
				.created_at(createdDate)
				.loan(loan2)
				.build();
		LoanTransaction loanTransaction3=LoanTransaction.builder()
				.transactionId(3L)
				.loan_amount(new BigDecimal("2000000"))
				.tenure(5)
				.userId(2L)
				.created_at(createdDate)
				.loan(loan3)
				.build();
		loanTransactions=new ArrayList<>();
		loanTransactions.add(loanTransaction1);
		loanTransactions.add(loanTransaction2);
		loanTransactions.add(loanTransaction3);
		Mockito.when(transactionRepository.findLoanTransactionByUserId(1L)).thenReturn(List.of(loanTransactions.get(0)));
		Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loans.get(0)));
		Mockito.when(loanRepository.findById(2L)).thenReturn(Optional.empty());
		Mockito.when(loanRepository.findById(3L)).thenReturn(Optional.of(loan3));
		Mockito.when(transactionRepository.save(loanTransactions.get(0))).thenReturn(loanTransactions.get(0));
	}

	@Test
	public void whenValidUserId_ReturnListOfTransaction() {
		Long id=1L;
		List<LoanTransaction> transactionsById = transactionService.getTransactionsByUserId(id);
		assertEquals(loanTransactions.get(0), transactionsById.get(0),"matched");
		
	}
	@Test
	public void whenValidLoanIdAndValidAmount_saveAndReturnTransaction() throws LoanNotFoundException, LoanAmountLimitException {
		LoanTransaction savedTransaction = transactionService.saveTransaction(loanTransactions.get(0));
		assertEquals(loanTransactions.get(0), savedTransaction);
	}
	@Test
	public void whenInvalidLoanIdAndValidAmount_throwLoanNotFoundException() throws LoanNotFoundException, LoanAmountLimitException {
		
		assertThrows(LoanNotFoundException.class, ()->transactionService.saveTransaction(loanTransactions.get(1)));
	}
	@Test
	public void whenValidLoanIdButInvalidAmount_throwLoanAmountLimitException() throws LoanNotFoundException, LoanAmountLimitException {
		
		assertThrows(LoanAmountLimitException.class, ()->transactionService.saveTransaction(loanTransactions.get(2)));
	}

}
