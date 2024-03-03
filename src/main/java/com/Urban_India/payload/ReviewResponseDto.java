package com.Urban_India.payload;

import com.Urban_India.entity.BusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ReviewResponseDto {


    private Long id;
    private Double rating;
    private Long userId;
    private String userName;
    private Long businessServiceId;
    private String businessServiceName;
    private String description;
}
