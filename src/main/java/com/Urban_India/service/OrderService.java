package com.Urban_India.service;

import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderPlacedDto;
import org.springframework.stereotype.Service;


public interface OrderService {

    public OrderDto orderPlaced(OrderPlacedDto orderPlacedDto);
}
