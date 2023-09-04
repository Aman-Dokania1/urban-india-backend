package com.Urban_India.payload;

import com.Urban_India.Enum.StatusEnum;
import com.Urban_India.entity.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Schema(name = "this Dto is used for coupon related stuff")
public class CouponDto {

    private LocalDate startTime;
    private LocalDate endTime;
    private Long id;
    private Double percent;
    private String code;
    private StatusEnum status;
    private Double minimumAmount;
    private String description;

    public Coupon toCoupon(){
        return Coupon.builder()
                .id(this.id)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .percent(this.percent)
                .code(this.code)
                .minimumAmount(this.minimumAmount)
                .status(this.status)
                .description(this.description).build();
    }

}
