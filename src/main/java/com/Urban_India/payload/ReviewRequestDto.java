package com.Urban_India.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDto {

    @NotNull
    private Double rating;

    private String description;
}
