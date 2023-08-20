package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.BusinessDto;
import com.Urban_India.payload.DiscountDto;
import com.Urban_India.service.BusinessService;
import com.Urban_India.service.ImageDataService;
import com.Urban_India.util.MapperUtil;
import com.Urban_India.util.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<BusinessDto> createBusiness(@Valid @RequestParam("data") String data, @RequestParam(name = "file",required = false) MultipartFile file ) throws IOException {
        Image image =null;
        if(Objects.nonNull(file) && !file.isEmpty()){
            image=imageDataService.saveImage(file);
        }
        BusinessDto businessDto=mapper.readValue(data,BusinessDto.class);
        businessDto.setImage(image);
        BusinessDto businessDto1=businessService.createBusiness(businessDto);
        return new ResponseEntity<>(businessDto1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<BusinessDto> getAllBusiness(){
        return businessService.getAllBusiness();
    }

    @PostMapping("/discount/{id}")
    public ResponseEntity<DiscountDto> createDiscount(@PathVariable(value = "id") Long businessId, @RequestBody DiscountDto discountDto){
//        logger.error("called discount controller");
        DiscountDto createdDiscount=discountService.createDiscount(businessId,discountDto);
        return new ResponseEntity<DiscountDto>(createdDiscount, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<Response<BusinessDto>> getBusinessServiceById(@RequestParam(value = "id",required = true) Long id){
        Response<BusinessDto> response = new Response<>();
        try {
            BusinessDto businessDto  = this.businessService.getBusinessById(id);
            response.setDto(businessDto);
            response.setHttpStatus(HttpStatus.OK);
        }catch (Exception ex){
            response.setErrorMessage(ex.getMessage());
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
