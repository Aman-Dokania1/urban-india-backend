package com.Urban_India.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response <T>{
    private T dto;
    private T rejectedDto;
    private HttpStatus httpStatus;
    private String errorMessage;
    private String successMessage;
}
