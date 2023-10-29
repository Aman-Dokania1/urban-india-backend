package com.Urban_India.service;

import com.Urban_India.payload.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    public CartDto addCartItem(CartDto cartDto);

}
