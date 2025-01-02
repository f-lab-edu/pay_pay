package com.sumin.paypay.model.response;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.sumin.paypay.model.dto.AccountDto;

@Getter
@Builder
@RequiredArgsConstructor
public class AccountDetailResponse {
	private final Long accountId;
	private final String accountNumber;
	private final BigDecimal balance;
	private final Instant transactionAt;
	private final Instant createdAt;
	private final Instant updatedAt;

	public static AccountDetailResponse of(AccountDto accountDto) {
		return AccountDetailResponse
			.builder()
			.accountId(accountDto.getAccountId())
			.accountNumber(accountDto.getAccountNumber())
			.balance(accountDto.getBalance())
			.createdAt(accountDto.getCreatedAt())
			.updatedAt(accountDto.getUpdatedAt())
			.build();
	}
}
