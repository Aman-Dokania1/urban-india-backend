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
        return Cart.builder().user(this.user)
                .cartItems(Objects.isNull(this.cartItemsDtos) ? null : MapperUtil.mapList(this.cartItemsDtos, CartItem.class))
                .business(Objects.isNull(this.businessDto) ? null : this.businessDto.toBusiness()).build();
    }
}
