package com.sumin.paypay.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TransferReqDto {

	private Long fromAccountId;
	private Long toAccountId;

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	private BigDecimal amount;
}
