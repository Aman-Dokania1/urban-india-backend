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

import java.util.List;

@Service
public class ServiceProviderImpl implements ServiceProvider {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private MapperUtil mapperUtil;
    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        ServiceProviderEntitiy service=mapperUtil.mapObject(serviceDto, ServiceProviderEntitiy.class);
        ServiceProviderEntitiy saveService=serviceRepository.save(service);
        return mapperUtil.mapObject(saveService, ServiceDto.class);
    }

    @Override
    public List<ServiceDto> getAllService() {
        List<ServiceProviderEntitiy> serviceProviderEntitiyList = serviceRepository.findAll();
        return mapperUtil.mapList(serviceProviderEntitiyList,ServiceDto.class);
    }
}
