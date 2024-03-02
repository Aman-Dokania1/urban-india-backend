package com.Urban_India.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaginatedDto<T> {
    private List<T> data;
    private Integer page;
    private Integer per;
    private Long total;
}
