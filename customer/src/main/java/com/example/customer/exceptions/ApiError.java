package com.example.customer.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String timestamp;
    private String reason;
    private String message;
    //private List<ApiSubError> subErrors;
    private List<String> messageList;

    private ApiError() {
        timestamp = LocalDateTime.now().toString();
    }

    public ApiError(HttpStatus status) {
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this.status = status;
        this.reason = "Unexpected error";
        this.message = ex.getLocalizedMessage();
        timestamp = LocalDateTime.now().toString();
    }

    public ApiError(HttpStatus status, String reason, Throwable ex) {
        this.status = status;
        this.reason = reason;
        this.message = ex.getLocalizedMessage();
        timestamp = LocalDateTime.now().toString();
    }

    public ApiError(HttpStatus status, String reason, String message) {
       // subErrors = new ArrayList<>();
    	messageList = new ArrayList<>();
        timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.reason = reason;
        this.message = message;
    }
    
    public ApiError(HttpStatus status, List<String> messageList) {
        timestamp = LocalDateTime.now().toString();
        this.messageList= messageList;
        this.status= status;
    }
}
