package com.Urban_India.service;

import com.Urban_India.payload.CartItemDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    public CartItemDto addCartItem(CartItemDto cartDto);

    public void deleteCartItem(Long cartItemId);

}
