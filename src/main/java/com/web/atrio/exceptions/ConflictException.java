package com.web.atrio.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends HttpException{
	public ConflictException(){
		super();
		this.setMessage("Object already exists");
		this.setStatus(HttpStatus.CONFLICT);
	}
}
