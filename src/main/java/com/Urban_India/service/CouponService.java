package com.Urban_India.service;

import com.Urban_India.entity.Coupon;
import com.Urban_India.payload.CouponDto;

import java.util.List;

public interface CouponService {

    public CouponDto addCoupon(CouponDto couponDto);

    public CouponDto updateCoupon(CouponDto couponDto);

    public void deleteCoupon (CouponDto couponDto);

    public List<CouponDto> getAllCoupon();

    public  CouponDto getCouponById(Long id);
}
