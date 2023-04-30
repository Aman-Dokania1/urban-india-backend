package com.Urban_India.service.impl;

import com.Urban_India.entity.Business;
import com.Urban_India.entity.Discount;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.payload.DiscountDto;
import com.Urban_India.repository.BusinessRepository;
import com.Urban_India.repository.DiscountRepository;
import com.Urban_India.service.DiscountService;
import com.Urban_India.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private MapperUtil mapperUtil;
    @Override
    public DiscountDto createDiscount(Long businessId, DiscountDto discountDto) {
        Business business= businessRepository.findById(businessId).orElseThrow(()->new ResourceNotFoundException("business","id",String.valueOf(businessId)));
        Discount discount=mapperUtil.mapObject(discountDto, Discount.class);
        discount.setBusiness(business);
        Discount saveDiscount=discountRepository.save(discount);
        System.out.println(business.getDiscountList());
        return mapperUtil.mapObject(saveDiscount, DiscountDto.class);
    }
}
