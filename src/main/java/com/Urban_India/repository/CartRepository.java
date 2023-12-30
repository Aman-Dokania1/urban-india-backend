package com.Urban_India.repository;

import com.Urban_India.entity.Cart;
import com.Urban_India.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query()
    public Cart findByUser(User user);
}
