package com.Urban_India.controller;

import com.Urban_India.payload.ReviewRequestDto;
import com.Urban_India.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

//    TODO: POST & GET & DELETE & UPDATE orderItem/:id/reviews , GET business/:id/reviews, GET businessService/:id/reviews

    @Autowired
    ReviewService reviewService;

    @PostMapping("orderItem/{orderItemId}/reviews")
    public void createReview(@PathVariable Long orderItemId, @RequestBody ReviewRequestDto reviewRequest){
        reviewService.postReview(orderItemId, reviewRequest);
    }
}
