package com.Urban_India.repository;

import com.Urban_India.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.id DESC")
    public Page<Order> getUsersOrders(Long userId, Pageable pageable);

//    @Query("SELECT o FROM Order o JOIN OrderItem oit ON o.id = oit.orderId WHERE (COALESCE(:orderItem) IS NULL OR oit.id IN (:orderItem))")
//    public Page<Order> getAllOrderOfOrderItems(@Nullable List<Long> orderItem);

}
