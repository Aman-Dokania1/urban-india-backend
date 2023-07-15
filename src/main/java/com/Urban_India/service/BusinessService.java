package com.Urban_India.service;

import com.Urban_India.payload.BusinessDto;

import java.util.List;

public interface BusinessService {
     public BusinessDto createBusiness(BusinessDto businessDto);

     public List<BusinessDto> getAllBusiness();

     public BusinessDto getBusinessById(Long id);

}
