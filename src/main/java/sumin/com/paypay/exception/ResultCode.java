package sumin.com.paypay.exception;

import org.springframework.http.HttpStatus;

public interface ResultCode {

	HttpStatus getStatus();

	String getMessage();
}
