package com.Urban_India.service;

import com.Urban_India.payload.OrderPlacedDto;
import org.springframework.stereotype.Service;


public interface OrderService {

    public void orderPlaced(OrderPlacedDto orderPlacedDto);
}
