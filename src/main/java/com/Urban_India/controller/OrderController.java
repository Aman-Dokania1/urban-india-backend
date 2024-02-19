package com.Urban_India.controller;

import com.Urban_India.payload.BusinessServiceDto;
import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderFilter;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.service.OrderService;
import com.Urban_India.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
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
    private ResponseEntity<Response> getAllOrderes(@RequestParam("per") Integer per, @RequestParam("page") Integer page, @RequestParam("paginate") Boolean paginate ){
        Response<Page<OrderDto>> response = new Response<>();
        Page<OrderDto> orders = orderService.getAllOrders(per, page, paginate);
        response.setHttpStatus(HttpStatus.OK);
        response.setDto(orders);
        response.setSuccessMessage("Order lists");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
