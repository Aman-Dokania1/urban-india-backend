package com.Urban_India.payload;

import com.Urban_India.entity.CartItem;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CartItemDto {

    private Long id;
    private Long businessServiceId;
    private Long cartId;
    private Long quantity;
    private BusinessServiceDto businessService;
    private LocalDate completionDate;
//    private CartDto cartDto;

    public CartItem toCartItem(){
        return CartItem.builder()
                .id(this.id)
                .businessService(Objects.nonNull(this.businessService) ? this.businessService.toBusinessService() : null)
//                .cart(Objects.isNull(this.cartDto) ? null :this.cartDto.toCart())
                .quantity(this.quantity)
                .completionDate(this.completionDate)
                .build();
    }
}
