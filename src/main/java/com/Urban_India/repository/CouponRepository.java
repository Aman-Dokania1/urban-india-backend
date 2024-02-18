package com.Urban_India.repository;

import com.Urban_India.entity.BusinessService;
import com.Urban_India.entity.Coupon;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

     @Query(value = "SELECT c FROM Coupon c WHERE c.business.id = :businessId")
    public Page<Coupon> getAllFilterCoupons(Long businessId, Pageable pageable );


}
