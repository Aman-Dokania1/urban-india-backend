package com.Urban_India.service;

import com.Urban_India.payload.BusinessServiceDto;

import java.util.List;

public interface BusinessServiceProvider {
    public BusinessServiceDto createBusinessService(BusinessServiceDto businessServiceDto);

    public List<BusinessServiceDto> getAllBusinessService();
}
