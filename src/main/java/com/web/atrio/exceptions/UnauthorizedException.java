package com.web.atrio.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException{

	public UnauthorizedException() {
		super();
		this.setMessage("Full authentication is required to access this resource");
		this.setStatus(HttpStatus.FORBIDDEN);
	}
}
