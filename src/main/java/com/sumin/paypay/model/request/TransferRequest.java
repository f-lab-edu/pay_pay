package com.sumin.paypay.model.request;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransferRequest {

	private final Long fromAccountId;
	private final Long toAccountId;
	private final BigDecimal amount;
}
