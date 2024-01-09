package com.Urban_India.payload;

import com.Urban_India.entity.BusinessService;
import com.Urban_India.entity.Cart;
import com.Urban_India.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CartItemDto {

    private Long businessServiceId;
    private Long cartId;
    private Long quantity;
    private BusinessServiceDto businessService;
    private CartDto cartDto;

    public CartItem toCartItem(){
        return CartItem.builder()
                .businessService(Objects.nonNull(this.businessService) ? this.businessService.toBusinessService() : null)
                .cart(Objects.isNull(this.cartDto) ? null :this.cartDto.toCart())
                .quantity(this.quantity)
                .build();
    }
}
