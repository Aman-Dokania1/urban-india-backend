package com.Urban_India.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <T>{
    private T dto;
    private T rejectedDto;
    private HttpStatus httpStatus;
    private String errorMessage;
    private String successMessage;
}
