package com.web.atrio.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException{

	public BadRequestException() {
		super();
		this.setMessage("Bad request");
		this.setStatus(HttpStatus.BAD_REQUEST);
	}

}
