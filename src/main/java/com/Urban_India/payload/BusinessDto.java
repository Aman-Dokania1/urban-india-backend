package com.Urban_India.payload;

import com.Urban_India.entity.Address;
import com.Urban_India.entity.Image;
import com.Urban_India.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto {
    private Address address;
    private Image image;
    private String documents;
}
