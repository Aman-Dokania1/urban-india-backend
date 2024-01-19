package com.Urban_India.service.impl;

import com.Urban_India.entity.BusinessService;
import com.Urban_India.entity.Cart;
import com.Urban_India.entity.CartItem;
import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.CartItemDto;
import com.Urban_India.repository.BusinessRepository;
import com.Urban_India.repository.BusinessServiceRepository;
import com.Urban_India.repository.CartItemRepository;
import com.Urban_India.repository.CartRepository;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;

    private CartRepository cartRepository;

    private CartItemRepository cartItemRepository;

    private BusinessServiceRepository businessServiceRepository;



    @Override
    public List<CartItemDto> getAllCartItems(){
        User user = currentUser();
        Cart cart = cartRepository.findByUser(user);
        if (Objects.isNull(cart))
            return new ArrayList<CartItemDto>();
        return cart.getCartItems().stream().map(CartItem::toCartItemDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartItemDto addCartItem(CartItemDto cartItemDto) {
        User user = currentUser();
        Cart cart = cartRepository.findByUser(user);
        BusinessService businessService = businessServiceRepository.findById(cartItemDto.getBusinessServiceId()).orElseThrow(()->new ResourceNotFoundException("BusinessService","id",null));
        // Created cart for user if cart is not exist
        if(Objects.isNull(cart)){
            cart = cartRepository.save(Cart.builder().business(businessService.getBusiness()).user(user).build());
        }
        // If cart is not belong to any business then set business with business of cart item
        if(Objects.isNull(cart.getBusiness())){
            cart.setBusiness(businessService.getBusiness());
        }
        if(!cart.getBusiness().getId().equals(businessService.getBusiness().getId())){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Can not add "+businessService.getTitle()+" in cart. Because user has cart with business "+ businessService.getBusiness().getName());
        }
        CartItem cartItem = CartItem.builder().businessService(businessService).cart(cart).build();
        return cartItemRepository.save(cartItem).toCartItemDto();
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new ResourceNotFoundException("CartItem","id",null));
        User user = currentUser();
        Cart cart = cartRepository.findByUser(user);

        // If cart is not exist for user
        if(Objects.isNull(cart)){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Cart is not exist for userName "+user.getUsername());
        }

        if(!cart.getBusiness().getId().equals(cartItem.getBusinessService().getBusiness().getId())){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"User has cart for different business. Business is "+cart.getBusiness().getName());
        }

        if(cart.getCartItems().contains(cartItem)){
            // If cart item contains only one item then we should remove business with cart.
            cartItemRepository.deleteById(cartItemId);
            if(cart.getCartItems().isEmpty()){
                cart.setBusiness(null);
                cartRepository.save(cart);
            }
        }else {
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Cart is not containing cart item with id "+ cartItemId);
        }
    }


    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }
}
