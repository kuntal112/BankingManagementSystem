package com.casestudy.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {
	@Id
	private Long loan_id;
	private String loan_type;
	private BigDecimal max_loan_amount;
	private int interest_rate;
	private int max_duration;
	
}
