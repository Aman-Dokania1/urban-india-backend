package com.Urban_India.controller;

import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.payload.PaginatedDto;
import com.Urban_India.service.OrderService;
import com.Urban_India.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{orderId}")
    private ResponseEntity<Response<OrderDto>> orderDetails(@PathVariable Long orderId){
        Response<OrderDto> response = new Response<>();
        OrderDto orderDto = orderService.getOrderById(orderId);
        response.setHttpStatus(HttpStatus.OK);
        response.setDto(orderDto);
        response.setSuccessMessage("Order is placed successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Response<OrderDto>> placedOrder(@RequestBody OrderPlacedDto orderPlacedDto){
       Response<OrderDto> response = new Response<>();
       OrderDto orderDto = orderService.orderPlaced(orderPlacedDto);
       response.setHttpStatus(HttpStatus.CREATED);
       response.setDto(orderDto);
       response.setSuccessMessage("Order is placed successfully");
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<Response<PaginatedDto<OrderDto>>> getAllOrders(@RequestParam(name = "per", defaultValue = "10" ) Integer per, @RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "paginate", defaultValue = "true") boolean paginate ){
        Response<PaginatedDto<OrderDto>> response = new Response<>();
        PaginatedDto<OrderDto> paginatedOrders = orderService.getAllOrders(per, page, paginate);
        response.setHttpStatus(HttpStatus.OK);
        response.setDto(paginatedOrders);
        response.setSuccessMessage("Order lists");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
