package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.payload.ReviewRequestDto;
import com.Urban_India.repository.*;
import com.Urban_India.service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    BusinessServiceRepository businessServiceRepo;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Review postReview(Long orderItemId, ReviewRequestDto reviewRequest) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new ResourceNotFoundException("OrderItem","id", String.valueOf(orderItemId)));
        Optional<BusinessService> businessService = businessServiceRepo.findById(orderItem.toOrdertItemDto().getBusinessServiceId());
        Review review = Review.builder()
                .businessService(businessService.orElseGet(()-> null))
                .business(businessService.map(BusinessService::getBusiness).orElseGet(()-> null))
                .user(currentUser())
                .rating(reviewRequest.getRating())
                .description(reviewRequest.getDescription())
                .build();
        calcAverageRating(review, "add");
        reviewRepository.save(review);
        return review;
    }


    private void calcAverageRating(Review review, String action){

        long multiplier = action.equals("remove") ? -1L : 1L;


        if(Objects.nonNull(review.getBusiness())){
            Business business = review.getBusiness();
            Long newTotal = business.getTotalReviews() + multiplier;
            Double newAverage  = business.getAverageRating() * business.getTotalReviews() + review.getRating() * multiplier / newTotal;
            business.setAverageRating(newAverage);
            business.setTotalReviews(newTotal);
            businessRepository.save(business);
        }
        if(Objects.nonNull(review.getBusinessService())){
            BusinessService businessService = review.getBusinessService();
            Long newTotal = businessService.getTotalReviews() + multiplier;
            Double newAverage  = businessService.getAverageRating() * businessService.getTotalReviews() + review.getRating() * multiplier / newTotal;
            businessService.setAverageRating(newAverage);
            businessService.setTotalReviews(newTotal);
            businessServiceRepo.save(businessService);
        }
    }

    private User currentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }
}
