package com.Urban_India.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDto {

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Double rating;

    private String description;
}
