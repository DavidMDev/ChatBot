package com.web.atrio.exceptions;

import org.springframework.http.HttpStatus;

public class HttpException extends Exception {
	private String message;
	private HttpStatus status;

	public HttpException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public HttpException() {
		super();
	}

}
