package com.Urban_India.payload;

import com.Urban_India.entity.BusinessService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CartDto {

    private Long businessServiceId;
}
