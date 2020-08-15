package com.example.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@Getter
@AllArgsConstructor
public class RestTemplateException extends RuntimeException {
	
	private static final long serialVersionUID = -685982495391955312L;
	
	public RestTemplateException(String message) {
		super(message);
	}

}
