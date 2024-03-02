package com.Urban_India.payload;

import com.Urban_India.entity.BusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ReviewResponseDto {


    private Double rating;
    private Long userId;
    private Long businessServiceId;
    private String businessServiceName;
    private String description;
}
