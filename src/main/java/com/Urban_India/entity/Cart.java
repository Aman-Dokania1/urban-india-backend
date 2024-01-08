package com.Urban_India.entity;

import jakarta.persistence.*;
import jdk.jfr.Threshold;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    List<CartItem> cartItems;

}
