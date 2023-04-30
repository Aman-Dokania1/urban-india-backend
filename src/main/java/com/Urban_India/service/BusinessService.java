package com.Urban_India.service;

import com.Urban_India.payload.BusinessDto;

import java.util.List;

public interface BusinessService {
     BusinessDto createBusiness(BusinessDto businessDto);

     List<BusinessDto> getAllBusiness();
}
