package com.Urban_India.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPlacedDto {

    @NotNull(message = "Address can't be null.")
    private Long addressId;
    private Long couponId;
}
