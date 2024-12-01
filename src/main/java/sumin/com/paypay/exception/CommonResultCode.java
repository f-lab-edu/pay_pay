package sumin.com.paypay.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum CommonResultCode implements ResultCode {

	SUCCESS(OK, "success"),

	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server error"),
	INVALID_DATA(HttpStatus.BAD_REQUEST, "invalid data"),
	INVALID_INPUT_ERROR(HttpStatus.BAD_REQUEST, "invalid input error"),
	URI_NOT_FOUND(NOT_FOUND, "url not found");


	private final HttpStatus status;
	private final String message;

	CommonResultCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
