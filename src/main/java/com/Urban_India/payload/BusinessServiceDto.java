package com.Urban_India.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessServiceDto {

    private String title;
    private String description;
    private double price;
    private Long mode_id;
    private Long addressId;
    private Long businessId;
    private Long serviceId;
    private Long statusId;
}
