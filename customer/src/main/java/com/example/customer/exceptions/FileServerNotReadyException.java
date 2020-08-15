package com.example.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@Getter
@AllArgsConstructor
public class FileServerNotReadyException extends RuntimeException {
	
	private static final long serialVersionUID = -581938415638622091L;
	
	public FileServerNotReadyException(String message) {
		super (message);
	}
	

}
