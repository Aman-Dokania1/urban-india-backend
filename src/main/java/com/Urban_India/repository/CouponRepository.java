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

     @Query("SELECT c FROM Coupon c WHERE (COALESCE(:businessIds) IS NUll OR c.business.id IN :businessIds)")
    public Page<Coupon> getAllFilterCoupons(List<Long> businessIds, Pageable pageable );


}
