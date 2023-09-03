package com.Urban_India.service;

import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.BusinessServiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BusinessServiceProvider {
    public BusinessServiceDto createBusinessService(BusinessServiceDto businessServiceDto, MultipartFile multipartFile) throws IOException;

    public List<BusinessServiceDto> getAllBusinessServices();

    public  BusinessServiceDto getBusinessSeriveById(Long id);

    public Page<BusinessServiceDto> getAllFilterBusinessServices(BusinessServiceFilter businessServiceFilter);
}
