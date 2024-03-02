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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Business business=businessDto.toBusiness();
        business.setUser(user);
        Address address=addressService.saveAddress(business.getAddress());
        business.setAddress(address);
        Business savedBusiness=businessRepository.save(business);
        user.setBusiness(business);
        userRepository.save(user);
        return business.toBusinessDto();
    }

    @Override
    public List<BusinessDto> getAllBusiness() {
        List<Business> businessList = businessRepository.findAll();
        return mapperUtil.mapList(businessList,BusinessDto.class);
    }

    @Override
    public BusinessDto getBusinessById(Long id) {
        Business business = businessRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Business","id",id.toString()));
        return mapperUtil.mapObject(business, BusinessDto.class);
    }


    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user =userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
        return user;
    }
}
