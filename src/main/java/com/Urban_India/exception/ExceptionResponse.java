package com.Urban_India.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
class ExceptionResponse<T extends Exception> {

  @JsonIgnoreProperties({"stackTrace", "parameter"})
  private T exception;
  private String errorMessage;
  private Map<String,Object> errors;

  public ExceptionResponse(T exception,String errorMessage){
    this.errorMessage = errorMessage;
    this.exception = exception;
  }

  public ExceptionResponse(T exception, String errorMessage, Map<String,Object> errors){
    this(exception,errorMessage);
    this.errors = errors;
  }

}