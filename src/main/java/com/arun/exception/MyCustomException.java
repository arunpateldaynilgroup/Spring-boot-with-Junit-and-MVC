package com.arun.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCustomException extends Exception {

	private static final long serialVersionUID = 1L;

	final ExceptionCode code;
	final ExceptionType type;
	final String message;
	
	public MyCustomException(ExceptionCode code, ExceptionType type, String message) {
		this.code = code;
		this.type = type;
		this.message = message;
	}

}
