package com.sumin.paypay.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sumin.paypay.type.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TransferResDto {

	private Long id;
	private Long fromAccountId;
	private Long toAccountId;
	private BigDecimal amount;
	private String currencyType;
	private String bankCode;
	private BigDecimal fee;
	private TransactionType transactionType;
	private LocalDateTime createdAt;
}
