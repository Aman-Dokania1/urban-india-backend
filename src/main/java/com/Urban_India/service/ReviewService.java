package com.Urban_India.service;

import com.Urban_India.entity.Review;
import com.Urban_India.payload.ReviewRequestDto;


public interface ReviewService {

    public Review postReview(Long orderItemId, ReviewRequestDto reviewRequest);

}
