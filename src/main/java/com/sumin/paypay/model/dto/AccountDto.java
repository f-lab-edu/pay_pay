package com.sumin.paypay.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccountDto {
	private Long accountId;
	private Long userId;
	private String accountNumber;
	private BigDecimal balance;
	private Instant transactionAt;
	private Instant createdAt;
	private Instant updatedAt;
}
