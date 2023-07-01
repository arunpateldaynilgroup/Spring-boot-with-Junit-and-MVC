package com.arun.exception;

import com.arun.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MyCustomException.class)
	public final ResponseEntity<Object> handleAllExceptions(MyCustomException e) {
		if(e.getType() == ExceptionType.INFO)
			log.info("Info => "+ e.getLocalizedMessage());
		else if(e.getType() == ExceptionType.DEBUG)
			log.info("Debug => ", e);
		else 
			log.error("Error => ", e);
		return ResponseEntity.status(e.getCode().getCode()).body(getExceptionResponse(e.getCode(), e.getLocalizedMessage()));
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> unhandleExceptions(Exception e) {
		log.error("Error =>",e);
		return ResponseEntity.status(ExceptionCode.INTERNAL_SERVER_ERROR.getCode()).body(getExceptionResponse(ExceptionCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(ExceptionCode.VALIDATION_FAILED.getCode()).body(getExceptionResponse(ExceptionCode.VALIDATION_FAILED, ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))));
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return ResponseEntity.status(ExceptionCode.ARGUMENT_TYPE_MISMATCH.getCode()).body(getExceptionResponse(ExceptionCode.ARGUMENT_TYPE_MISMATCH, String.format("'%s' should be a '%s' and your send '%s' isn't valid", ex.getName(), ex.getRequiredType().getSimpleName(), ex.getValue())));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(ExceptionCode.MESSAGE_NOT_READABLE_EXCEPTION.getCode()).body(getExceptionResponse(ExceptionCode.MESSAGE_NOT_READABLE_EXCEPTION, ex.getLocalizedMessage()));
	}

	private ExceptionResponse getExceptionResponse(ExceptionCode code, Object error) {
		return new ExceptionResponse(code, error, DateUtil.getDefaultDateAndTimeFormat(new Date()));
	}

}
