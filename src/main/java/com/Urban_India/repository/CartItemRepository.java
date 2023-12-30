package com.Urban_India.repository;

import com.Urban_India.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    
}
