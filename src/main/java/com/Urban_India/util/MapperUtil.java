package com.Urban_India.util;

import com.Urban_India.entity.*;
import com.Urban_India.payload.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {

    private ModelMapper mapper=new ModelMapper();

//    public ServiceProviderEntitiy mapToService(ServiceDto serviceDto){
//        return mapper.map(serviceDto, ServiceProviderEntitiy.class);
//    }
//
//    public ServiceDto mapToServoceDto(ServiceProviderEntitiy service){
//        return mapper.map(service,ServiceDto.class);
//    }
//
//    public Business mapToBusiness(BusinessDto businessDto){
//        return mapper.map(businessDto,Business.class);
//    }
//
//    public BusinessDto mapToBusinessDto(Business business){
//        return mapper.map(business, BusinessDto.class);
//    }
//
//    public AddressDto mapToAddressDto(Address address){
//        return mapper.map(address, AddressDto.class);
//    }
//
//    public Address mapToAddress(AddressDto addressDto){
//        return mapper.map(addressDto,Address.class);
//    }
//
//    public DiscountDto mapToDiscountDto(Discount discount){
//        return mapper.map(discount, DiscountDto.class);
//    }
//
//    public Discount mapToDiscount(DiscountDto discountDto){
//        return mapper.map(discountDto, Discount.class);
//    }
//
//    public BusinessServiceDto mapTOBusinessServiceDto(BusinessService businessService){
//        return mapper.map(businessService,BusinessServiceDto.class);
//    }
//
//    public BusinessService mapToBusinessService(BusinessServiceDto businessServiceDto){
//        return mapper.map(businessServiceDto,BusinessService.class);
//    }
//
    public <S,T> T mapObject(S soruce, Class<T> targetClass){
        return mapper.map(soruce,targetClass);
    }

    public <S,T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
