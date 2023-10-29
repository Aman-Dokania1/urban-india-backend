package com.Urban_India.controller;

import com.Urban_India.payload.CartDto;
import com.Urban_India.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @PostMapping
    private ResponseEntity<Response<CartDto>> addCartItem(@RequestBody CartDto cartDto, @AuthenticationPrincipal){
        Response<CartDto> response = new Response<>();
        try {

        }
    }
}
