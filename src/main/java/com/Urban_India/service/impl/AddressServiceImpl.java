package com.Urban_India.service.impl;

import com.Urban_India.entity.Address;
import com.Urban_India.payload.AddressDto;
import com.Urban_India.repository.AddressRepository;
import com.Urban_India.service.AddressService;
import com.Urban_India.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MapperUtil mapperUtil;
    @Override
    public Address saveAddress(Address address) {
        Address savedAddress=addressRepository.save(address);
        return savedAddress;
    }


}
