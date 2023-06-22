package com.Urban_India.service;

import com.Urban_India.payload.ServiceDto;

import java.util.List;

public interface ServiceProvider {
    public ServiceDto createService(ServiceDto serviceDto);

    public List<ServiceDto> getAllService();
}
