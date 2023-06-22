package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.BusinessDto;
import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.repository.ImageRepositroy;
import com.Urban_India.service.BusinessServiceProvider;
import com.Urban_India.service.ImageDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Objects;

@RestController
@RequestMapping("/api/businessService")
@AllArgsConstructor
public class BusinessServiceController {

    private BusinessServiceProvider businessServiceProvider;

    private ImageDataService imageDataService;

    private ObjectMapper objectMapper;
    @PostMapping
    private ResponseEntity<BusinessServiceDto> createBusinessService(@Valid @RequestParam("data") String data, @RequestParam(name = "file",required = false) MultipartFile file ) throws IOException {
        Image image =null;
        if(Objects.nonNull(file) && !file.isEmpty()){
            image=imageDataService.saveImage(file);
        }
        BusinessServiceDto businessServiceDto=objectMapper.readValue(data,BusinessServiceDto.class);
        System.out.println(businessServiceDto);
        BusinessServiceDto businessServiceDto1=businessServiceProvider.createBusinessService(businessServiceDto);
        return new ResponseEntity<>(businessServiceDto1, HttpStatus.CREATED);
    }
}
