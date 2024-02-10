package com.Urban_India.controller;

import com.Urban_India.payload.OrderPlacedDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    private void placedOrder(@RequestBody OrderPlacedDto orderPlacedDto){

    }
}
