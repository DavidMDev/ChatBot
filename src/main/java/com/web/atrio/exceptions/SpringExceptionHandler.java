package com.web.atrio.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
public class SpringExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(HttpException.class)
	@ResponseBody
	public ResponseEntity<String> handleUnauthorizedException(HttpException e){
		return new ResponseEntity<String>(e.getMessage(), e.getStatus());
	}
	
	}
