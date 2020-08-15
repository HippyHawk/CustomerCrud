package com.example.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@AllArgsConstructor
public class InvalidUriException extends RuntimeException{

	
	private static final long serialVersionUID = 6127979597900762794L;
	public InvalidUriException(String message) {
		super (message);
	}

}
