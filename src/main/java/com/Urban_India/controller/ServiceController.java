package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.ServiceDto;
import com.Urban_India.service.ImageDataService;
import com.Urban_India.service.ServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/service")
public class ServiceController {

    private ImageDataService imageDataService;
    private ObjectMapper mapper;
    private ServiceProvider serviceProvider;

    @PostMapping
    private ResponseEntity<ServiceDto> createService(@Valid @RequestParam("data") String data, @RequestParam("file") MultipartFile file) throws IOException {

        Image image=imageDataService.saveImage(file);
        ServiceDto serviceDto=mapper.readValue(data,ServiceDto.class);
        serviceDto.setImage(image);
        ServiceDto serviceDto1=serviceProvider.createService(serviceDto);
        return new ResponseEntity<ServiceDto>(serviceDto1, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<ServiceDto>> getAllService(){
        List<ServiceDto> serviceDtoList = serviceProvider.getAllService();
        return new ResponseEntity<>(serviceDtoList,HttpStatus.OK);
    }
}
