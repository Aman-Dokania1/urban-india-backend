package com.Urban_India.exception;

import com.Urban_India.util.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  {


    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<ExceptionResponse<ResourceNotFoundException>> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
//        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ExceptionResponse<>(exception,exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UrbanApiException.class)
    private ResponseEntity<ExceptionResponse<UrbanApiException>> handleUrbanApiException(UrbanApiException urbanApiException, WebRequest webRequest){
//        ErrorDetails errorDetails=new ErrorDetails(new Date(),urbanApiException.getMessage(),webRequest.getDescription(false),urbanApiException.getStatus());
        return new ResponseEntity<>(new ExceptionResponse<>(urbanApiException, urbanApiException.getMessage()), urbanApiException.getStatus());
    }


    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ExceptionResponse<AccessDeniedException>> handleResourceNotFoundException(AccessDeniedException exception, WebRequest webRequest){
//        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false),HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(new ExceptionResponse<>(exception, exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ExceptionResponse<AccessDeniedException>> handleUnauthorizeException(AccessDeniedException exception,
                                                                         WebRequest webRequest){
//        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(new ExceptionResponse<>(exception, exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionResponse<MethodArgumentNotValidException>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest webRequest) {

        Map<String,Object> error=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err)->{
            String fieldName=((FieldError) err).getField();
            String message=err.getDefaultMessage();

            error.put(fieldName,message);
        });
        return new ResponseEntity<>(new ExceptionResponse<>(ex,ex.getMessage(),error),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<ExceptionResponse<Exception>> handleGlobalException(HttpRequestMethodNotSupportedException exception,
                                                                               WebRequest webRequest){
//        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ExceptionResponse<>(exception, exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse<Exception>> handleGlobalException(Exception exception,
                                                                WebRequest webRequest){
//        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ExceptionResponse<>(exception, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
