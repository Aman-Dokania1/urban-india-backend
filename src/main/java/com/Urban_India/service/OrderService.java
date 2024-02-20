package com.Urban_India.service;

import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.payload.PaginatedDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface OrderService {

    public OrderDto orderPlaced(OrderPlacedDto orderPlacedDto);

    public PaginatedDto<OrderDto> getAllOrders(Integer per, Integer page, Boolean paginate);
    public OrderDto getOrderById(Long orderId);
}
