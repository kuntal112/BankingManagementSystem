package com.casestudy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.entities.Loan;
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long>{

}
