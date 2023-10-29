package com.Urban_India.repository;

import com.Urban_India.entity.Cart;
import com.Urban_India.entity.CartItem;
import com.Urban_India.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query()
    public Cart findByUser(User user);
}
