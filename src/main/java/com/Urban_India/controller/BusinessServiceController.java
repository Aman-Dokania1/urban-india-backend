package com.Urban_India.controller;

import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.service.BusinessServiceProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/businessProvider")
@AllArgsConstructor
public class BusinessServiceController {

    private BusinessServiceProvider businessServiceProvider;
    @PostMapping
    private ResponseEntity<BusinessServiceDto> createBusinessService(@RequestBody BusinessServiceDto businessServiceDto){
        System.out.println(businessServiceDto);
        BusinessServiceDto businessServiceDto1=businessServiceProvider.createBusinessService(businessServiceDto);
        return new ResponseEntity<>(businessServiceDto1, HttpStatus.CREATED);
    }
}
