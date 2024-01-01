package com.Urban_India.entity;

import com.Urban_India.payload.CartItemDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id",referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "business_service_id", referencedColumnName = "id")
    private BusinessService businessService;

    public CartItemDto toCartItemDto(){
        return CartItemDto.builder()
                .cartId(Objects.isNull(this.cart) ? null :this.cart.getId())
                .businessServiceId(Objects.isNull(this.businessService) ? null :this.businessService.getId())
                .quantity(this.quantity)
                .businessService(this.businessService)
                .cart(this.cart)
                .build();
    }

}
