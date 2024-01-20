package com.Urban_India.payload;

import com.Urban_India.entity.Business;
import com.Urban_India.entity.Cart;
import com.Urban_India.entity.CartItem;
import com.Urban_India.entity.User;
import com.Urban_India.util.MapperUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private User user;
    private BusinessDto businessDto;
    List<CartItemDto> cartItemsDtos;

    public Cart toCart(){
        return Cart.builder()
//                .user(this.user)
                .cartItems(Objects.isNull(this.cartItemsDtos) ? null : this.cartItemsDtos.stream().map(cartItemDto -> cartItemDto.toCartItem()).collect(Collectors.toList()))
                .business(Objects.isNull(this.businessDto) ? null : this.businessDto.toBusiness())
                .build();
    }
}
