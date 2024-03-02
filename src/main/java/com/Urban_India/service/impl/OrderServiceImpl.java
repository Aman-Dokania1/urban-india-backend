package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.OrderDto;
import com.Urban_India.payload.OrderPlacedDto;
import com.Urban_India.payload.PaginatedDto;
import com.Urban_India.repository.*;
import com.Urban_India.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
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
        User user = currentUser();
        Cart cart = cartRepository.findByUser(user);

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

        double orderPrice = 0.0;
        List<OrderItem> orderItemList = new ArrayList<>();
        orderPrice = cartItems.stream().map(cartItem -> cartItem.getBusinessService().getPrice()).
        reduce(0.0,Double::sum);

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
                .price(orderPrice)
                .build();

        Order finalOrder = order;
        cartItems.forEach(cartItem -> {
            orderItemList.add(cartItem.convertToOrderItem(finalOrder));
        });
        order.setOrderItems(orderItemList);
        order = orderRepository.save(order);
        clearCart(cart);
        return order.toOrderDto();
    }

    @Override
    public PaginatedDto<OrderDto> getAllOrders(Integer per, Integer page, Boolean paginate) {
        User user = currentUser();
        Pageable pageable = paginate ? PageRequest.of(page, per) : Pageable.unpaged();
        Page<Order> ordersPage = orderRepository.getUsersOrders(user.getId(),pageable);
        List<OrderDto> orderDtoList = ordersPage.stream().map(Order::toOrderDto).toList();
        return new PaginatedDto<>(orderDtoList, page, per, ordersPage.getTotalElements());
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order", "id", String.valueOf(orderId)));
        return order.toOrderDto();
    }

    public void clearCart(Cart cart){
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.setBusiness(null);
        cart.setCartItems(null);
        cartRepository.save(cart);
    }


    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }
}
