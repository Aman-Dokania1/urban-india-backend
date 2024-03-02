package com.Urban_India.service;

import com.Urban_India.entity.Review;
import com.Urban_India.payload.PaginatedDto;
import com.Urban_India.payload.ReviewRequestDto;
import com.Urban_India.payload.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ReviewService {

    public ReviewResponseDto postReview(Long orderItemId, ReviewRequestDto reviewRequest);

    public List<ReviewResponseDto> getOrderItemReviews(List<Long> orderItemId);

    public ReviewResponseDto updateOrderItemReview(Long orderItemId, ReviewRequestDto reviewRequestDto);

    public void deleteOrderItemReview(Long orderItemId);

    public PaginatedDto<ReviewResponseDto> getBusinessReviews(List<Long> businessIds, Pageable pageable);

    public PaginatedDto<ReviewResponseDto> getBusinessServiceReviews(List<Long> businessServiceIds, Pageable pageable);
}
