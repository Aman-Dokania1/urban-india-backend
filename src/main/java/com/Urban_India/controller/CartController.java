package com.Urban_India.controller;

import com.Urban_India.payload.CartItemDto;
import com.Urban_India.service.CartService;
import com.Urban_India.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping
    private ResponseEntity<Response<CartItemDto>> addCartItem(@RequestBody CartItemDto cartDto ){
        Response<CartItemDto> response = new Response<>();
        cartDto = cartService.addCartItem(cartDto);
        response.setDto(cartDto);
        response.setHttpStatus(HttpStatus.CREATED);
        response.setSuccessMessage("Cart Item is Added Successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{itemId}")
    private ResponseEntity<Response<String>> deleteCartItem(@PathVariable("itemId") Long itemId){
        Response<String> response = new Response<>();
        cartService.deleteCartItem(itemId);
        response.setSuccessMessage("Cart Item is deleted Successfully");
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
