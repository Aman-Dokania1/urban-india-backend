package com.Urban_India.service.impl;

import com.Urban_India.entity.*;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.PaginatedDto;
import com.Urban_India.payload.ReviewRequestDto;
import com.Urban_India.payload.ReviewResponseDto;
import com.Urban_India.repository.*;
import com.Urban_India.service.ReviewService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final Map<String,Integer> reviewActions = Map.of("ADD",1,"REMOVE",-1,"UPDATE",0);

    @Override
    @Transactional
    public ReviewResponseDto postReview(Long orderItemId, ReviewRequestDto reviewRequest) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new ResourceNotFoundException("OrderItem","id", String.valueOf(orderItemId)));
        Optional<BusinessService> businessService = businessServiceRepo.findById(orderItem.toOrdertItemDto().getBusinessServiceId());
        Review review = Review.builder()
                .businessService(businessService.orElseGet(()-> null))
                .business(businessService.map(BusinessService::getBusiness).orElseGet(()-> null))
                .user(getCurrentUser())
                .rating(reviewRequest.getRating())
                .description(reviewRequest.getDescription())
                .build();
        calcAverageRating(review, "ADD",0.0);
        reviewRepository.save(review);
        return review.toReviewResponseDto();
    }

    @Override
    public List<ReviewResponseDto> getOrderItemReviews(List<Long> orderItemIds) {
        List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);
        if(CollectionUtils.isEmpty(orderItems)) {
            throw new ResourceNotFoundException("OrderItem", "id", StringUtils.join(orderItemIds , ","));
        }
        User user = getCurrentUser();

        List<OrderItem> invalidOrderItemIds = orderItems.stream()
                .filter(orderItem -> orderItem.getOrder().getUser().getId().equals(user.getId())).toList();
        if(!CollectionUtils.isEmpty(invalidOrderItemIds)){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"OrderItemIds doesn't exists. OrderItem Ids are "+ StringUtils.join(invalidOrderItemIds, "-"));
        }

        return reviewRepository.getOrderItemReviews(orderItemIds).stream().map(Review::toReviewResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReviewResponseDto updateOrderItemReview(Long orderItemId, ReviewRequestDto reviewRequestDto) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new ResourceNotFoundException("OrderItem","id", String.valueOf(orderItemId)));
        isReviewEditableByCurrentUser(orderItemId,orderItem);
        Review review = orderItem.getReview();
        Double previousRating = review.getRating();
        review.setDescription(reviewRequestDto.getDescription());
        review.setRating(reviewRequestDto.getRating());
        calcAverageRating(review, "UPDATED",previousRating);
        return reviewRepository.save(review).toReviewResponseDto();
    }

    @Transactional
    @Override
    public void deleteOrderItemReview(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new ResourceNotFoundException("OrderItem","id", String.valueOf(orderItemId)));
        isReviewEditableByCurrentUser(orderItemId,orderItem);
        Review review = orderItem.getReview();
        Double previousRating = review.getRating();
        review.setRating(0.0);
        calcAverageRating(review, "DELETE",previousRating);
        reviewRepository.deleteById(orderItem.getReview().getId());
    }

    @Override
    public PaginatedDto<ReviewResponseDto> getBusinessReviews(List<Long> businessIds, Pageable pageable) {
        Page<Review> businessReviews = reviewRepository.getBusinessReviews(businessIds,pageable);
        List<ReviewResponseDto> reviewResponseDtos = businessReviews.getContent()
                .stream()
                .map(Review::toReviewResponseDto)
                .toList();

        return  PaginatedDto.<ReviewResponseDto>builder()
                .data(reviewResponseDtos)
                .page(businessReviews.getNumber())
                .per(businessReviews.getSize())
                .total(businessReviews.getTotalElements())
                .build();
    }

    @Override
    public PaginatedDto<ReviewResponseDto> getBusinessServiceReviews(List<Long> businessServiceIds, Pageable pageable) {
        Page<Review> businessServiceReviews = reviewRepository.getBusinessServiceReviews(businessServiceIds,pageable);
        List<ReviewResponseDto> reviewResponseDtos = businessServiceReviews.getContent()
                .stream()
                .map(Review::toReviewResponseDto)
                .toList();

        return  PaginatedDto.<ReviewResponseDto>builder()
                .data(reviewResponseDtos)
                .page(businessServiceReviews.getNumber())
                .per(businessServiceReviews.getSize())
                .total(businessServiceReviews.getTotalElements())
                .build();
    }


    private void calcAverageRating(Review review, String action, Double previousRating){

        long newTotal = review.getBusinessService().getTotalReviews();
        Double newRating = review.getRating();
        Double totalExistingRating = 0.0;
        previousRating = -1*previousRating;
        if(reviewActions.containsKey(action)){
            int value = reviewActions.get(action);
        }else{
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY, action + " action is not applicable on reviews.");
        }

        if(Objects.nonNull(review.getBusiness())){
            Business business = review.getBusiness();
            totalExistingRating = business.getAverageRating() * business.getTotalReviews();
            Double newAverage  = ( totalExistingRating + previousRating + newRating)/ newTotal;
            business.setAverageRating(newAverage);
            business.setTotalReviews(newTotal);
            businessRepository.save(business);
        }
        if(Objects.nonNull(review.getBusinessService())){
            BusinessService businessService = review.getBusinessService();
            totalExistingRating = businessService.getAverageRating() * businessService.getTotalReviews();
            Double newAverage  = ( totalExistingRating + previousRating + newRating)/ newTotal;
            businessService.setAverageRating(newAverage);
            businessService.setTotalReviews(newTotal);
            businessServiceRepo.save(businessService);
        }
    }

    private User getCurrentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new ResourceNotFoundException("user","username",username));
    }

    private boolean isReviewEditableByCurrentUser(Long orderItemId,OrderItem orderItem){
        User user = getCurrentUser();
        if(!Objects.equals(orderItem.getOrder().getUser().getId(), user.getId())){
            throw new UrbanApiException(HttpStatus.UNPROCESSABLE_ENTITY,"OrderItemIds doesn't exists. OrderItem Id is "+ String.valueOf(orderItemId));
        }
        return true;
    }
}
