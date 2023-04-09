package com.Urban_India.payload;

import com.Urban_India.entity.BusinessService;
import com.Urban_India.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private String title;
    private String description;
    private Image image;
    private List<BusinessService> businessServices;

}
