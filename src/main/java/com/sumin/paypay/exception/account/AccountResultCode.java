package com.sumin.paypay.exception.account;

import org.springframework.http.HttpStatus;

import com.sumin.paypay.exception.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountResultCode implements ResultCode {

	NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 계좌입니다");

	private final HttpStatus status;
	private final String message;

}
