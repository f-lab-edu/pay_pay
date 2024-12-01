package sumin.com.paypay.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.bridge.IMessage;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;
import sumin.com.paypay.common.ApiResponseEnvelop;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> processValidationError(
		MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getFieldErrors()
			.stream().map(error -> error.getField() + error.getDefaultMessage())
			.collect(Collectors.joining());
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.INVALID_INPUT_ERROR,
			message);
		log.info(exception.getMessage(), exception);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> methodNotSupportedException() {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.URI_NOT_FOUND);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> missingParameterError(
		MissingServletRequestParameterException exception) {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.INVALID_INPUT_ERROR);
		log.info(exception.getMessage(), error);

		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> methodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception) {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.INVALID_INPUT_ERROR);
		log.info(exception.getMessage(), error);

		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> commonError(CommonException exception) {
		log.info(exception.getMessage(), exception);
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			exception.getResultCode(), exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> httpMessageNotReadableError(
		HttpMessageNotReadableException exception) {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.INVALID_INPUT_ERROR);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(BeanInstantiationException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> handleBeanInstantiationException(
		BeanInstantiationException exception) {
		if (exception.getCause() instanceof CommonException commonException)
			return commonError(commonException);

		if (exception.getCause() instanceof IllegalArgumentException illegalArgumentException)
			return handleIllegalArgumentException(illegalArgumentException);

		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.SERVER_ERROR);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> handleNoResourceFoundException(
		NoResourceFoundException exception) {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.URI_NOT_FOUND);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> handleIllegalArgumentException(
		IllegalArgumentException exception) {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.INVALID_DATA, exception.getMessage()
		);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseEnvelop<?>> handleException(Exception ex) {
		log.error("Global exception: ", ex);
		return ResponseEntity.badRequest().body(ApiResponseEnvelop.error(CommonResultCode.SERVER_ERROR));
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody>> handleThrowable(Throwable throwable) {
		ApiResponseEnvelop<ApiResponseEnvelop.ErrorBody> error = ApiResponseEnvelop.error(
			CommonResultCode.SERVER_ERROR);
		log.error(throwable.getMessage(), throwable);
		return new ResponseEntity<>(error, HttpStatus.valueOf(error.getHttpStatus()));
	}

}
