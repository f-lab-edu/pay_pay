package sumin.com.paypay.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;
import sumin.com.paypay.exception.ResultCode;

@ToString
@Getter
public class ApiResponseEnvelop<T> {

	private String status;
	private int httpStatus;
	private T data;
	private T error;

	public ApiResponseEnvelop() {
	}

	public ApiResponseEnvelop(String status, int httpStatus, T data, T error) {
		this.status = status;
		this.httpStatus = httpStatus;
		this.data = data;
		this.error = error;
	}


	public static <T> ApiResponseEnvelop<T> success(T data) {
		return new ApiResponseEnvelop<>("SUCCESS", HttpStatus.OK.value(), data, null);
	}

	public static <T> ApiResponseEnvelop success() {
		return new ApiResponseEnvelop<>("SUCCESS", HttpStatus.OK.value(), null, null);
	}

	public static ApiResponseEnvelop<ErrorBody> error(ResultCode resultCode) {
		return new ApiResponseEnvelop<>("ERROR", resultCode.getStatus().value(), null,
			new ErrorBody(resultCode.getMessage()));
	}

	public static ApiResponseEnvelop<ErrorBody> error(ResultCode resultCode, String message) {
		return new ApiResponseEnvelop<>("ERROR", resultCode.getStatus().value(), null, new ErrorBody(message));
	}

	public static <T> ApiResponseEnvelop<T> multiMessageError(ResultCode resultCode, T message) {
		return new ApiResponseEnvelop<>("ERROR", resultCode.getStatus().value(), null,
			message);
	}

	public static final class ErrorBody {
		private final String message;

		public ErrorBody(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
