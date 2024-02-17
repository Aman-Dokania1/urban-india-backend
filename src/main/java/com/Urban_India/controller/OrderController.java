package com.Urban_India.controller;

import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.service.OrderService;
import com.Urban_India.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping
    private ResponseEntity<Response<OrderDto>> placedOrder(@RequestBody OrderPlacedDto orderPlacedDto){
       Response<OrderDto> response = new Response<>();
       OrderDto orderDto = orderService.orderPlaced(orderPlacedDto);
       response.setHttpStatus(HttpStatus.CREATED);
       response.setDto(orderDto);
       response.setSuccessMessage("Order is placed successfully");
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
