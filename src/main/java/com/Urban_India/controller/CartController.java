package com.Urban_India.controller;

import com.Urban_India.payload.CartDto;
import com.Urban_India.service.CartService;
import com.Urban_India.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping
    private ResponseEntity<Response<CartDto>> addCartItem(@RequestBody CartDto cartDto){
        Response<CartDto> response = new Response<>();

        cartService.addCartItem(cartDto);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
