package com.Urban_India.payload;

import com.Urban_India.entity.BusinessService;
import com.Urban_India.entity.Cart;
import com.Urban_India.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CartItemDto {

    private Long businessServiceId;
    private Long cartId;
    private Long quantity;
    private BusinessService businessService;
    private Cart cart;

    public CartItem toCartItem(){
        return CartItem.builder()
                .businessService(this.businessService)
                .cart(this.cart)
                .quantity(this.quantity)
                .build();
    }
}
