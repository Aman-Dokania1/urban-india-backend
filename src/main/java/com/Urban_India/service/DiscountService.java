package com.Urban_India.service;

import com.Urban_India.payload.DiscountDto;

public interface DiscountService {
    public DiscountDto createDiscount(Long businessId,DiscountDto discountDto);
}
