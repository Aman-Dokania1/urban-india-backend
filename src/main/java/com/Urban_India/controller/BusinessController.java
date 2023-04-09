package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.BusinessDto;
import com.Urban_India.payload.DiscountDto;
import com.Urban_India.service.BusinessService;
import com.Urban_India.service.DiscountService;
import com.Urban_India.service.ImageDataService;
import com.Urban_India.util.MapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/business")
@AllArgsConstructor
public class BusinessController {

    private ImageDataService imageDataService;
    private BusinessService businessService;
    private DiscountService discountService;
    private MapperUtil mapperUtil;
    private ObjectMapper mapper;

//    private Logger logger= LoggerFactory.getLogger(BusinessController.class);
    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@Valid @RequestParam("data") String data, @RequestParam("file") MultipartFile file) throws IOException {
        Image image =imageDataService.saveImage(file);
        BusinessDto businessDto=mapper.readValue(data,BusinessDto.class);
        businessDto.setImage(image);
        BusinessDto businessDto1=businessService.createBusiness(businessDto);
        return new ResponseEntity<>(businessDto1, HttpStatus.CREATED);
    }

    @PostMapping("/discount/{id}")
    public ResponseEntity<DiscountDto> createDiscount(@PathVariable(value = "id") Long businessId, @RequestBody DiscountDto discountDto){
//        logger.error("called discount controller");
        log.error("lsadkjsa");
        DiscountDto createdDiscount=discountService.createDiscount(businessId,discountDto);
        return new ResponseEntity<DiscountDto>(createdDiscount, HttpStatus.CREATED);
    }
}
