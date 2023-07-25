package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.BusinessServiceFilter;
import com.Urban_India.repository.*;
import com.Urban_India.service.BusinessServiceProvider;
import com.Urban_India.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class BusinessServiceProviderImpl implements BusinessServiceProvider {

    private MapperUtil mapperUtil;
    private BusinessRepository businessRepository;
    private ServiceRepository serviceRepository;
    private AddressRepository addressRepository;
    private BusinessServiceRepository businessServiceRepository;
    private StatusRepository statusRepository;
    private UserRepository userRepository;
    @Override
    public BusinessServiceDto createBusinessService(BusinessServiceDto businessServiceDto) {
        BusinessService businessService = businessServiceDto.toBusinessService();
        Business business= currentUser().getBusiness();


        if(Objects.isNull(business)){
            throw new UrbanApiException(HttpStatus.NOT_FOUND,"First you need to create business in order to add service.");
        }

        Address address = addressRepository.save(businessService.getAddress());

        ServiceProviderEntitiy serviceProviderEntitiy=serviceRepository.findById(businessServiceDto.getServiceTypeId()).get();
//
//        Status status=statusRepository.findById(businessServiceDto.getStatusId())
//                .orElseThrow(()->new ResourceNotFoundException("status","id",String.valueOf(businessServiceDto.getStatusId())));

//        Discount discount=null;
//        if(businessServiceDto.getDiscountId()!=null){
//            discount=discountRepository.findById(businessServiceDto.getDiscountId())
//                    .orElseThrow(()->new ResourceNotFoundException("discount","id",String.valueOf(businessServiceDto.getDiscountId())));
//        }


//        if(businessServiceDto.getAddressId()!=null){
//            address=addressRepository.findById(businessServiceDto.getAddressId())
//                    .orElseThrow(()->new ResourceNotFoundException("address","id",String.valueOf(businessServiceDto.getAddressId())));
//        }


        businessService.setBusiness(business);
        businessService.setService(serviceProviderEntitiy);
        businessService.setAddress(address);

        BusinessService savedBusinessService=businessServiceRepository.save(businessService);
//        System.out.println(business.getBusinessServices());
//        System.out.println(serviceProviderEntitiy.getBusinessServices());
        BusinessServiceDto businessServiceDto1=savedBusinessService.toBusinessServiceDto();
        return businessServiceDto1;
    }

    @Override
    public List<BusinessServiceDto> getAllBusinessServices() {
        List<BusinessService> businessServices = this.businessServiceRepository.findAll();
        List<BusinessServiceDto> businessServiceDtoList  = businessServices.stream().map(businessService -> businessService.toBusinessServiceDto()).toList();
        return businessServiceDtoList;
    }

    @Override
    public BusinessServiceDto getBusinessSeriveById(Long id) {
        BusinessService businessService = this.businessServiceRepository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Business Service","id",id.toString()));
        return businessService.toBusinessServiceDto();
    }

    @Override
    public Page<BusinessServiceDto> getAllFilterBusinessServices(BusinessServiceFilter businessServiceFilter) {
        Pageable pageable = null;

        if(Objects.isNull(businessServiceFilter.getUnpaged()) && businessServiceFilter.getUnpaged()){
            pageable = Pageable.unpaged();
        }else {
            pageable = PageRequest.of(businessServiceFilter.getPage(), businessServiceFilter.getPer());
        }
        Page<BusinessService> businessServicePage = this.businessServiceRepository.getAllFilterBusinessService(
                businessServiceFilter.getListOfBusinessIds(),businessServiceFilter.getListOfBusinessServiceIds(),
                businessServiceFilter.getPriceRange().get(0),businessServiceFilter.getPriceRange().get(1),businessServiceFilter.getSearchQuery(),pageable);
        List<BusinessServiceDto> businessServiceDtoList  = businessServicePage.stream().map(businessService -> businessService.toBusinessServiceDto()).toList();
        return new PageImpl<>(businessServiceDtoList,pageable,businessServicePage.getTotalElements());
    }

    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user =userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
        return user;
    }
}
