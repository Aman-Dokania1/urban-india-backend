package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.repository.AddressRepository;
import com.Urban_India.repository.CartRepository;
import com.Urban_India.repository.CouponRepository;
import com.Urban_India.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public void orderPlaced(OrderPlacedDto orderPlacedDto) {

        Cart cart = cartRepository.findById(orderPlacedDto.getCartId()).orElseThrow(() ->new ResourceNotFoundException("Cart","id",orderPlacedDto.getCartId().toString()));

        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.isEmpty()){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Cart can't be empty.");
        }

        cartItems.forEach(cartItem -> {
            if(Objects.isNull(cartItem.getCompletionDate())){
                throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Completion date can't be null for "+cartItem.getBusinessService().getTitle());
            }
        });

        Address address = addressRepository.findById(orderPlacedDto.getAddressId()).orElseThrow(()-> new ResourceNotFoundException("Address","id",orderPlacedDto.getAddressId().toString()));
        Coupon coupon = null;
        if(Objects.nonNull(orderPlacedDto.getCouponId())) {
            coupon = couponRepository.findById(orderPlacedDto.getCouponId()).orElseThrow(()-> new ResourceNotFoundException("Coupon","id",orderPlacedDto.getCouponId().toString()));
        }

        Business business = cart.getBusiness();
        List<BusinessService> businessServices = business.getBusinessServices();

        Order order = Order.builder().
                userAddressId(orderPlacedDto.getAddressId()).
                address(address.getAddress())
                .couponId(Objects.isNull(coupon) ? null : coupon.getId())
                .couponCode(Objects.isNull(coupon) ? null : coupon.getCode())
                .couponPercentage(Objects.isNull(coupon) ? null : coupon.getPercent())
                .businessId(business.getId())
                .businessName(business.getName())
                .build();

    }

    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }
}
