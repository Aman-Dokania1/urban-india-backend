package com.Urban_India.repository;

import com.Urban_India.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
