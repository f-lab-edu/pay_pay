package com.sumin.paypay.exception.user;

import org.springframework.http.HttpStatus;

import com.sumin.paypay.exception.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResultCode implements ResultCode {

	INVALID_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다.");


	private final HttpStatus status;
	private final String message;
}
