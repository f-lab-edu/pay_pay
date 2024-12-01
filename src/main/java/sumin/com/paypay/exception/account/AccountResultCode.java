package sumin.com.paypay.exception.account;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sumin.com.paypay.exception.ResultCode;

@Getter
@RequiredArgsConstructor
public enum AccountResultCode implements ResultCode {

	INVALID(HttpStatus.BAD_REQUEST, "");

	private final HttpStatus status;
	private final String message;

}
