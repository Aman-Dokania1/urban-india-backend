package com.Urban_India.service;

import com.Urban_India.payload.CartItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    public CartItemDto addCartItem(CartItemDto cartDto);

    public List<CartItemDto> getAllCartItems();

    public void deleteCartItem(Long cartItemId);

    public CartItemDto updateCartItem(CartItemDto cartItemDto);

}
