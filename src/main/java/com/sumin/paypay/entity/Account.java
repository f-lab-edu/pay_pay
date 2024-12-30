package com.sumin.paypay.entity;

import java.math.BigDecimal;
import java.time.Instant;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "update account set deleted = true where id = ?")
@Builder
@Entity
@Table(name = "account")
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "balance", nullable = false, precision = 19, scale = 2)
	private BigDecimal balance;

	@Column(name = "transaction_at")
	private Instant transactionAt;

	@Column(name = "created_at", nullable = false)
	@CreationTimestamp
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private Instant updatedAt;

	@Column(name = "deleted_at")
	private Instant deletedAt;

	public static Account of(Long userId, String accountNumber) {
		return Account.builder()
			.userId(userId)
			.accountNumber(accountNumber)
			.balance(BigDecimal.ZERO)
			.build();
	}

	public void changeBalance(BigDecimal balance) {
		this.balance = balance;
	}

}