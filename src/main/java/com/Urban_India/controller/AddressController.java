package com.Urban_India.controller;

import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.payload.AddressDto;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.AddressService;
import com.Urban_India.service.impl.AddressServiceImpl;
import com.Urban_India.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    private ResponseEntity<Response<AddressDto>> addAddress(@RequestBody AddressDto addressDto){
        User user = getCurrentUser();
        addressDto =  addressService.saveCurrentUserAddress(addressDto,user);
        Response<AddressDto> response = new Response<>();
        response.setDto(addressDto);
        response.setHttpStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    private ResponseEntity<Response<AddressDto>> getAddressById(@PathVariable Long id){
        AddressDto addressDto = addressService.getAddressById(id);
        Response<AddressDto> response = new Response<>();
        response.setDto(addressDto);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<Response<List<AddressDto>>> getAllAddressOfCurrentUser(){
        User user = getCurrentUser();
        List<AddressDto> addressDtoList =  addressService.getAllAddressOfCurrentUser(user);
        Response<List<AddressDto>> response = new Response<>();
        response.setDto(addressDtoList);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("{id}")
    private ResponseEntity<Response<AddressDto>> updateAddress(@RequestBody AddressDto addressDto ,@PathVariable Long id){
        addressDto = addressService.updateAddress(addressDto,id);
        Response<AddressDto> response = new Response<>();
        response.setDto(addressDto);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Deleting the address of current user .
     * Note that if address belongs to any business or business service in that case we need to write separate logic and API.
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    private ResponseEntity<Response<String>> deleteAddressById(@PathVariable Long id){
        User user = getCurrentUser();
        addressService.deleteAddressById(id,user);
        Response<String> response = new Response<>();
        response.setSuccessMessage("Address is deleted Successfully");
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private User getCurrentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }
}
