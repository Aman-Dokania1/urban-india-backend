package com.Urban_India.service;

import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.BusinessServiceFilter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BusinessServiceProvider {
    public BusinessServiceDto createBusinessService(BusinessServiceDto businessServiceDto);

    public List<BusinessServiceDto> getAllBusinessServices();

    public  BusinessServiceDto getBusinessSeriveById(Long id);

    public Page<BusinessServiceDto> getAllFilterBusinessServices(BusinessServiceFilter businessServiceFilter);
}
