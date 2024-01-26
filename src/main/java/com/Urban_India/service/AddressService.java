package com.Urban_India.service;

import com.Urban_India.entity.Address;
import com.Urban_India.entity.User;
import com.Urban_India.payload.AddressDto;

import java.util.List;

public interface AddressService {
    public Address saveAddress(Address address);

    public AddressDto saveCurrentUserAddress(AddressDto addressDto, User user);

    public AddressDto getAddressById(Long id);

    public List<AddressDto> getAllAddressOfCurrentUser(User user);

    public AddressDto updateAddress(AddressDto addressDto,Long id);

    public void deleteAddressById(Long id,User user);
}
