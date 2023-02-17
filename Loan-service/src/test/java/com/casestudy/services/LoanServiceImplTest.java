package com.casestudy.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.casestudy.entities.Loan;
import com.casestudy.repositories.LoanRepository;
@SpringBootTest
class LoanServiceImplTest {
	@Autowired
	LoanService loanService;
	@MockBean
	LoanRepository loanRepository;
	List<Loan> loans=new ArrayList<>();
	@BeforeEach
	void setUp() throws Exception {
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
		
		loans.add(loan1);
		loans.add(loan2);
		Mockito.when(loanRepository.findAll()).thenReturn(loans);
	}

	@Test
	public void findAll_Loans() {
		List<Loan> loansDetails = loanService.getLoansDetails();
		assertEquals(loans, loansDetails);
	}

}
