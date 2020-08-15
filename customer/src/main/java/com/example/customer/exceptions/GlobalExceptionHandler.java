package com.example.customer.exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.RollbackException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	 @Override
	    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
	                                                                  HttpStatus status, WebRequest request) {
	    	String error = "Malformed JSON request";
	        return buildResponseEntity(new ApiError(status, error, ex));
	    }

	    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	        return new ResponseEntity<>(apiError, apiError.getStatus());
	    }
	    
//	    @ExceptionHandler(ConstraintViolationException.class)
//	   // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	    public ResponseEntity<String> handleConstraintViolationException(HttpServletRequest request, Exception ex) {
//	        log.info("ConstraintViolationException Occured:: URL=" + request.getRequestURL());
//	       
//	        return new ResponseEntity<String> (ex.getMessage(),HttpStatus.BAD_REQUEST);
//	    }
	    
//	    @ExceptionHandler(RollbackException.class)
//	    //@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	    public ResponseEntity<String> handleRollbackException(HttpServletRequest request, Exception ex) {
//	        log.info("RollbackException Occured:: URL=" + request.getRequestURL());
//	        
//	        return new ResponseEntity<String> (ex.getMessage(),HttpStatus.BAD_REQUEST);
//	    }
	    
	    @ExceptionHandler(RollbackException.class)
	    public ResponseEntity<?> handleNotValidException(RollbackException ex){

	        String errMessage = ex.getCause().getMessage() ;

	        List<String> listErrMessage = getListErrMessage(errMessage);
	        ApiError response = new ApiError(
	                HttpStatus.NOT_ACCEPTABLE
	                ,listErrMessage
	                );

	        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);

	    }

	     public static List<String> getListErrMessage(String msg){

	         Stream<String> stream = Arrays.stream(msg.split("\n"))
	                 .filter(s -> s.contains("\t"))
	                 .map(s -> s.replaceAll("^([^\\{]+)\\{", ""))
	                 .map(s -> s.replaceAll("[\"]", ""))
	                 .map(s -> s.replaceAll("=", ":"))
	                 .map(s -> s.replaceAll("interpolatedMessage", "message"))
	                 .map(s -> s.replaceAll("\\{|\\}(, *)?", ""));

	         return stream.collect(Collectors.toList());
	     }
}
