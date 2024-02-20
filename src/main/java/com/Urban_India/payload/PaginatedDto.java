package com.Urban_India.payload;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginatedDto<T> {
    private List<T> data;
    private Integer page;
    private Integer per;
    private Long total;

}
