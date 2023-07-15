package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.BusinessDto;
import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.BusinessServiceFilter;
import com.Urban_India.repository.ImageRepositroy;
import com.Urban_India.service.BusinessServiceProvider;
import com.Urban_India.service.ImageDataService;
import com.Urban_India.util.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.List;
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
        businessServiceDto=businessServiceProvider.createBusinessService(businessServiceDto);
        return new ResponseEntity<>(businessServiceDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    private ResponseEntity<List<BusinessServiceDto>> getAllBusinessService(){
        List<BusinessServiceDto> businessServiceDtos = this.businessServiceProvider.getAllBusinessServices();
        return new ResponseEntity<>(businessServiceDtos,HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<Response<BusinessServiceDto>> getBusinessServiceById(@RequestParam(value = "id",required = true) Long id){
        Response<BusinessServiceDto> response = new Response<>();
        try {
            BusinessServiceDto businessServiceDto = this.businessServiceProvider.getBusinessSeriveById(id);
            response.setDto(businessServiceDto);
            response.setHttpStatus(HttpStatus.OK);
        }catch (Exception ex){
            response.setErrorMessage(ex.getMessage());
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/all-filter")
    private ResponseEntity<List<BusinessServiceDto>> getAllFilteredBusinessService(@RequestBody BusinessServiceFilter businessServiceFilter){
        List<BusinessServiceDto> businessServiceDtos = this.businessServiceProvider.getAllBusinessServices();
        return new ResponseEntity<>(businessServiceDtos,HttpStatus.OK);
    }
}
