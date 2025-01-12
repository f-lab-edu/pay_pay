package com.sumin.paypay.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

	INTER_TRANSACTION("내부 송금"),
	EXTERNAL_TRANSACTION("외부 송금");

	private final String description;
}
