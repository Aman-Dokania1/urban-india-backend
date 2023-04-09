package com.Urban_India.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String google_location_code;
    private String state;
    private String city;
    private String pin;
    private String plotNo;
}
