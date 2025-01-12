package com.sumin.paypay.model.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

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
	private LocalDateTime transactionAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
