package com.Urban_India.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BusinessServiceFilter {
    private List<Long> listOfBusinessIds;
    private List<Long> listOfBusinessServiceIds;
    private List<Long> listOfStatusIds;
    private Double minPrice;
    private Double maxPrice;
    private String searchQuery;
    private List<String> sortField[];
    private Integer per;
    private Integer page;
    @Builder.Default
    private Boolean unpaged = false;

}
