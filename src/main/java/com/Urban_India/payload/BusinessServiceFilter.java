package com.Urban_India.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessServiceFilter {
    private List<Long> listOfBusinessIds;
    private List<Long> listOfBusinessServiceIds;
    private List<Long> listOfStatusIds;
    private Double minPrice;
    private Double maxPrice;
    private String searchQuery;
    private List<String> sortField[];
}
