package com.Urban_India.service.impl;

import com.Urban_India.entity.Address;
import com.Urban_India.entity.Business;
import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.payload.BusinessDto;
import com.Urban_India.repository.BusinessRepository;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.AddressService;
import com.Urban_India.service.BusinessService;
import com.Urban_India.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private MapperUtil mapperUtil;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressService addressService;

    @Override
    public BusinessDto createBusiness(BusinessDto businessDto) {
        User user=currentUser();
        Business business=mapperUtil.mapToBusiness(businessDto);
        business.setUser(user);
        Address address=addressService.saveAddress(business.getAddress());
        business.setAddress(address);
        Business savedBusiness=businessRepository.save(business);
        return mapperUtil.mapToBusinessDto(savedBusiness);
    }

    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user =userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
        return user;
    }
}
