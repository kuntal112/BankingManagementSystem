package com.casestudy.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "loan_transaction")
@Builder
public class LoanTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	@Column(name="user_id")
	private Long userId;
	@Column(name = "loan_principal")
	private BigDecimal loan_amount;

	@Column(name = "tenure")
	private double tenure;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date created_at;

	@OneToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;
}


