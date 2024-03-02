package com.Urban_India.repository;

import com.Urban_India.entity.BusinessService;
import com.Urban_India.payload.BusinessServiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessServiceRepository extends JpaRepository<BusinessService,Long> {

    @Query("SELECT bs FROM BusinessService bs WHERE (COALESCE(:businessIds) IS NUll OR bs.business.id IN (:businessIds))" +
            "AND (COALESCE(:businessServiceIds) IS NULL OR bs.service.id IN (:businessServiceIds))" +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR bs.price BETWEEN :minPrice AND :maxPrice )" +
            "AND (:searchQuery IS NULL OR bs.title LIKE %:searchQuery%)")
    public Page<BusinessService> getAllFilterBusinessService(List<Long> businessIds,List<Long> businessServiceIds,Double minPrice,Double maxPrice,String searchQuery,Pageable pageable);

}
