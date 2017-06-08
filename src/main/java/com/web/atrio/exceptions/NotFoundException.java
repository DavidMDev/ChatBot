package com.web.atrio.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {

	public NotFoundException(){
		super();
		this.setMessage("Resource not found.");
		this.setStatus(HttpStatus.NOT_FOUND);
	}
}
