package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.repository.*;
import com.Urban_India.service.BusinessServiceProvider;
import com.Urban_India.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusinessServiceProviderImpl implements BusinessServiceProvider {

    private MapperUtil mapperUtil;
    private BusinessRepository businessRepository;
    private ServiceRepository serviceRepository;
    private AddressRepository addressRepository;
    private BusinessServiceRepository businessServiceRepository;
    private StatusRepository statusRepository;
    @Override
    public BusinessServiceDto createBusinessService(BusinessServiceDto businessServiceDto) {
//        BusinessService businessService=mapperUtil.mapToBusinessService(businessServiceDto);

        Business business=businessRepository.findById(businessServiceDto.getBusinessId()).
                orElseThrow(()->new ResourceNotFoundException("business","id",String.valueOf(businessServiceDto.getBusinessId())));

        ServiceProviderEntitiy serviceProviderEntitiy=serviceRepository.findById(businessServiceDto.getServiceId())
                .orElseThrow(()->new ResourceNotFoundException("service","id",String.valueOf(businessServiceDto.getServiceId())));

        Status status=statusRepository.findById(businessServiceDto.getStatusId())
                .orElseThrow(()->new ResourceNotFoundException("status","id",String.valueOf(businessServiceDto.getStatusId())));

//        Discount discount=null;
//        if(businessServiceDto.getDiscountId()!=null){
//            discount=discountRepository.findById(businessServiceDto.getDiscountId())
//                    .orElseThrow(()->new ResourceNotFoundException("discount","id",String.valueOf(businessServiceDto.getDiscountId())));
//        }

        Address address=null;
        if(businessServiceDto.getAddressId()!=null){
            address=addressRepository.findById(businessServiceDto.getAddressId())
                    .orElseThrow(()->new ResourceNotFoundException("address","id",String.valueOf(businessServiceDto.getAddressId())));
        }

        BusinessService businessService=new BusinessService();

        businessService.setTitle(businessServiceDto.getTitle());
        businessService.setDescription(businessServiceDto.getDescription());
        businessService.setPrice(businessServiceDto.getPrice());
        businessService.setMode_id(businessServiceDto.getMode_id());

        businessService.setBusiness(business);
        businessService.setService(serviceProviderEntitiy);
        businessService.setAddress(address);
        businessService.setStatus(status);

        BusinessService savedBusinessService=businessServiceRepository.save(businessService);
        System.out.println(business.getBusinessServices());
        System.out.println(serviceProviderEntitiy.getBusinessServices());
        BusinessServiceDto businessServiceDto1=mapperUtil.mapTOBusinessServiceDto(savedBusinessService);
        return businessServiceDto1;
    }
}
