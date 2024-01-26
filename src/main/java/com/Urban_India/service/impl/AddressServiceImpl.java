package com.Urban_India.service.impl;

import com.Urban_India.entity.Address;
import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.AddressDto;
import com.Urban_India.repository.AddressRepository;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.AddressService;
import com.Urban_India.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MapperUtil mapperUtil;

    @Autowired
    UserRepository userRepository;
    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public AddressDto saveCurrentUserAddress(AddressDto addressDto, User user) {
        Long userId =  user.getId();
        user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId.toString()));
        Address address = addressDto.toAddress();
        user.getAddressList().add(address);
        user = userRepository.save(user);
        return user.getAddressList().get(user.getAddressList().size()-1).toAddressDto();
    }

    @Override
    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("address","id",id.toString()));
        return address.toAddressDto();
    }

    @Override
    public List<AddressDto> getAllAddressOfCurrentUser(User user) {
        Long userId =  user.getId();
        user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId.toString()));
        return user.getAddressList().stream().map((Address::toAddressDto)).collect(Collectors.toList());
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto, Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("address","id",id.toString()));
        address = addressDto.toAddress();
        address.setId(id);
        return addressRepository.save(address).toAddressDto();
    }

    @Override
    public void deleteAddressById(Long id,User user) {
        Long userId =  user.getId();
        user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId.toString()));
        // Find the address by ID
        Optional<Address> optionalAddress = user.getAddressList()
                .stream()
                .filter(address -> address.getId().equals(id))
                .findFirst();
        if(optionalAddress.isPresent()){
            user.getAddressList().remove(optionalAddress.get());
            userRepository.save(user);
        }else{
            throw new UrbanApiException(HttpStatus.NOT_FOUND,"You don't have an address with given id. Id is "+id.toString()+" .");
        }
    }


}
