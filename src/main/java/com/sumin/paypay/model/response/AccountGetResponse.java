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
public class AccountGetResponse {
	private final Long accountId;
	private final Long userId;
	private final String accountNumber;
	private final BigDecimal balance;
	private final Instant transactionAt;
	private final Instant createdAt;
	private final Instant updatedAt;

	public static AccountGetResponse to(AccountDto dto) {
		return AccountGetResponse.builder()
			.accountId(dto.getAccountId())
			.userId(dto.getUserId())
			.accountNumber(dto.getAccountNumber())
			.balance(dto.getBalance())
			.transactionAt(dto.getTransactionAt())
			.createdAt(dto.getCreatedAt())
			.updatedAt(dto.getUpdatedAt())
			.build();
	}
}
