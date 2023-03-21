package com.Urban_India.exception;

import org.springframework.http.HttpStatus;

public class UrbanApiException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public UrbanApiException(HttpStatus status,String message){
        super(message);
        this.status=status;
        this.message=message;
    }

    public UrbanApiException(HttpStatus status,String message,String message1){
        super(message);
        this.status=status;
        this.message=message1;
    }
}
