package com.Urban_India.entity;

import com.Urban_India.payload.CartDto;
import com.Urban_India.payload.CartItemDto;
import com.Urban_India.util.MapperUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Threshold;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @JsonBackReference(value = "businessEntityAReference")
    private Business business;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_item_id",referencedColumnName = "id")
    @JsonManagedReference
    List<CartItem> cartItems;

    public void dismissCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }

//    public void dismissChildren() {
//        this.cartItems.forEach(CartItem::dismissCart); // SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
//        this.cartItems.clear();
//    }

    public CartDto toCartDto(){
        return CartDto.builder()
//                .user(this.user)
                .businessDto(Objects.isNull(this.business) ? null : this.business.toBusinessDto())
                .cartItemsDtos(Objects.isNull(this.cartItems) ? null : this.cartItems.stream().map(CartItem::toCartItemDto).collect(Collectors.toList()))
        .build();
    }

}
