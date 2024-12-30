package com.sumin.paypay.exception;

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

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> processValidationError(
		MethodArgumentNotValidException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> methodNotSupportedException(
		HttpRequestMethodNotSupportedException exception
	) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> missingParameterError(
		MissingServletRequestParameterException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ErrorResponse> commonError(CommonException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> httpMessageNotReadableError(
		HttpMessageNotReadableException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(BeanInstantiationException.class)
	public ResponseEntity<ErrorResponse> handleBeanInstantiationException(
		BeanInstantiationException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
		NoResourceFoundException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
		IllegalArgumentException exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.info(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse(exception.getMessage()));
	}
}
