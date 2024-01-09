package com.Urban_India.entity;

import com.Urban_India.payload.CartDto;
import com.Urban_India.payload.CartItemDto;
import com.Urban_India.util.MapperUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Threshold;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "business_id",referencedColumnName = "id")
    private Business business;

    @OneToMany(mappedBy = "cart")
    @JsonManagedReference(value = "cartItemEntityReference")
    List<CartItem> cartItems;

    public CartDto toCartDto(){
        return CartDto.builder()
                .user(this.user)
                .businessDto(Objects.isNull(this.business) ? null : this.business.toBusinessDto())
                .cartItemsDtos(Objects.isNull(this.cartItems) ? null : MapperUtil.mapList(this.cartItems, CartItemDto.class))
        .build();
    }

}
