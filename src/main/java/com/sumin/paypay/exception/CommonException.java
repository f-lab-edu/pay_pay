package com.sumin.paypay.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException{

	private final ResultCode resultCode;
	private final String message;
}
