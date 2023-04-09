package com.Urban_India.service.impl;

import com.Urban_India.entity.ServiceProviderEntitiy;
import com.Urban_India.payload.ServiceDto;
import com.Urban_India.repository.ServiceRepository;
import com.Urban_India.service.ServiceProvider;
import com.Urban_India.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderImpl implements ServiceProvider {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private MapperUtil mapperUtil;
    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        ServiceProviderEntitiy service=mapperUtil.mapToService(serviceDto);
        ServiceProviderEntitiy saveService=serviceRepository.save(service);
        return mapperUtil.mapToServoceDto(saveService);
    }
}
