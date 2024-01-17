package com.Urban_India.controller;

import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.CartItemDto;
import com.Urban_India.service.CartService;
import com.Urban_India.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    private ResponseEntity<Response<List<CartItemDto>>> getAllCartItems(){
        Response<List<CartItemDto>> response = new Response<>();
        List<CartItemDto> cartItems = cartService.getAllCartItems();
        response.setDto(cartItems);
        response.setHttpStatus(HttpStatus.OK);
        response.setSuccessMessage("Cart Items listed");
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping
    private ResponseEntity<Response<CartItemDto>> addCartItem(@RequestBody CartItemDto cartItemDtoDto ){
        Response<CartItemDto> response = new Response<>();
        cartItemDtoDto = cartService.addCartItem(cartItemDtoDto);
//        cartItemDtoDto.setCartDto(null);
//        cartItemDtoDto.getCartDto().setCartItemsDtos(null);
        response.setDto(cartItemDtoDto);
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
