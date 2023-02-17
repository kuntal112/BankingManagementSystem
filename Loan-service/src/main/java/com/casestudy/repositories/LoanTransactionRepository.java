package com.casestudy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.entities.LoanTransaction;
@Repository
public interface LoanTransactionRepository extends JpaRepository<LoanTransaction,Long>{
	List<LoanTransaction> findLoanTransactionByUserId(Long id);
}
