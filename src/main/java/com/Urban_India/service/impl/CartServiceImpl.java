package com.Urban_India.service.impl;

import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.payload.CartDto;
import com.Urban_India.repository.CartItemRepository;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;

    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addCartItem(CartDto cartDto) {
        User user = currentUser();
        return null;
    }

    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user =userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
        return user;
    }
}
