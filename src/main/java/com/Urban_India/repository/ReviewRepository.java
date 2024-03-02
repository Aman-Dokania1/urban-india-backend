package com.Urban_India.repository;

import com.Urban_India.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Nullable;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT rw from Review rw where (COALESCE(:orderItemIds) IS NULL OR rw.orderItem.id IN (:orderItem))")
    public List<Review> getOrderItemReviews(@Nullable List<Long> orderItemIds);

    @Query("SELECT rw FROM Review rw WHERE (COALESCE(:businessIds) IS NULL OR rw.business.id IN (:businessIds))")
    public Page<Review> getBusinessReviews(@Nullable List<Long> businessIds, Pageable pageable);

    @Query("SELECT rw FROM Review rw WHERE (COALESCE(:businessServiceIds) IS NULL OR rw.businessService.id IN (:businessServiceIds))")
    public Page<Review> getBusinessServiceReviews(@Nullable List<Long> businessServiceIds, Pageable pageable);

}
