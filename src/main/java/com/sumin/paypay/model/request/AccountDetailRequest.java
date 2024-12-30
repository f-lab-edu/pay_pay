package com.sumin.paypay.model.request;

import com.sumin.paypay.model.AuthRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//임시 권한 체크 추후 auth 적용해야함.
@Getter
@RequiredArgsConstructor
public class AccountDetailRequest implements AuthRequest {

	private final Long userId;

	@Override
	public Long getUserId() {
		return null;
	}
}
