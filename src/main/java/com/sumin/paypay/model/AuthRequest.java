package com.sumin.paypay.model;

import jakarta.validation.constraints.NotNull;

public interface AuthRequest {
	@NotNull
	Long getUserId();
}
