package com.Urban_India.service;

import com.Urban_India.entity.Coupon;
import com.Urban_India.payload.CouponDto;
import com.Urban_India.payload.CouponFilter;

import java.util.List;

public interface CouponService {

    public CouponDto addCoupon(CouponDto couponDto);

    public CouponDto updateCoupon(CouponDto couponDto,Long id);

    public void deleteCoupon (Long id);

    public List<CouponDto> getAllCoupon();

    public  CouponDto getCouponById(Long id);

    public List<CouponDto> getFilteredCoupons(Long businessId);
}
