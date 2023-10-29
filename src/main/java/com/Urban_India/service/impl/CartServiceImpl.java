package com.Urban_India.service.impl;

import com.Urban_India.entity.Business;
import com.Urban_India.entity.BusinessService;
import com.Urban_India.entity.Cart;
import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.CartDto;
import com.Urban_India.repository.BusinessRepository;
import com.Urban_India.repository.BusinessServiceRepository;
import com.Urban_India.repository.CartRepository;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;

    private CartRepository cartRepository;
    
    private BusinessRepository businessRepository;

    private BusinessServiceRepository businessServiceRepository;



    @Override
    public CartDto addCartItem(CartDto cartDto) {
        User user = currentUser();
        Cart cart = cartRepository.findByUser(user);
        Car
        cart.setCartItems();
        BusinessService businessServices = businessServiceRepository.findById(cartDto.getBusinessServiceId()).orElseThrow(()->new ResourceNotFoundException("BusinessService","id",null));
        if(Objects.isNull(cart)){
            cart = cartRepository.save(Cart.builder().business(businessServices.getBusiness()).user(user).build());
        }
        if(!cart.getBusiness().getId().equals(businessServices.getBusiness().getId())){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Can not add"+businessServices.getTitle()+"in cart");
        }
        cart = cartRepository.save(cart);
        return null;
    }

    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user =userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
        return user;
    }
}
