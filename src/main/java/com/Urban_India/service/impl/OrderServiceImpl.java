package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.repository.*;
import com.Urban_India.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OrderDto orderPlaced(OrderPlacedDto orderPlacedDto) {

        Cart cart = cartRepository.findById(orderPlacedDto.getCartId()).orElseThrow(() ->new ResourceNotFoundException("Cart","id",orderPlacedDto.getCartId().toString()));

        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.isEmpty()){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Cart can't be empty.");
        }

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
                .businessId(business.getId())
                .businessName(business.getName())
                .user(currentUser())
                .paymentId(null)
                .build();
        order = orderRepository.save(order);
        List<OrderItem> orderItemList = new ArrayList<>();
        Order finalOrder = order;
        cartItems.forEach(cartItem -> {
            orderItemList.add(cartItem.convertToOrderItem(finalOrder));
        });
        orderItemRepository.saveAll(orderItemList);
        order = orderRepository.findById(order.getId()).get();
        return order.toOrderDto();
    }


    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }
}
