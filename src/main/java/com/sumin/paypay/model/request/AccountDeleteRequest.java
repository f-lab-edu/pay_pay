package com.sumin.paypay.model.request;

import com.sumin.paypay.model.AuthRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountDeleteRequest implements AuthRequest {

	private final Long userId;

	@Override
	public Long getUserId() {
		return null;
	}
}
