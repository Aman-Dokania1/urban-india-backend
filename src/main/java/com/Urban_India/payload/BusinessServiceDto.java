package com.Urban_India.payload;


import com.Urban_India.entity.Address;
import com.Urban_India.entity.BusinessService;
import com.Urban_India.model.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BusinessServiceDto {
    private Long id;
    private String title;
    private String description;
    private double price;
    private Long mode_id;
    private Long serviceId;
    private Long statusId;
    private Long ServiceType;
    private AddressModel addressModel;

    public BusinessService toBusinessService(){
        return BusinessService.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .mode_id(this.mode_id)
                .address(new Address(null, Objects.nonNull(addressModel) ? addressModel.toString() : ""))
                .build();
    }
}
