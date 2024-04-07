package com.Urban_India.controller;

import com.Urban_India.payload.BaseFilter;
import com.Urban_India.payload.PaginatedDto;
import com.Urban_India.payload.ReviewRequestDto;
import com.Urban_India.payload.ReviewResponseDto;
import com.Urban_India.service.ReviewService;
import com.Urban_India.util.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.ListDataEvent;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("orderItem/{orderItemId}/reviews")
    public ResponseEntity<Response<ReviewResponseDto>> createReview(@PathVariable Long orderItemId,@Valid @RequestBody ReviewRequestDto reviewRequest){
        Response<ReviewResponseDto> response = new Response<>();
        ReviewResponseDto reviewResponseDto = reviewService.postReview(orderItemId, reviewRequest);
        response.setDto(reviewResponseDto);
        response.setHttpStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("orderItem/{orderItemId}/review")
    public ResponseEntity<Response<List<ReviewResponseDto>>> getOrderItemReviews(@PathVariable Long orderItemId){
        Response<List<ReviewResponseDto>> response = new Response<>();
        List<ReviewResponseDto> reviewResponseDtos = reviewService.getOrderItemReviews(List.of(orderItemId));
        response.setDto(reviewResponseDtos);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("orderItem/{orderItemId}/review")
    public ResponseEntity<Response<ReviewResponseDto>> updateOrderItemReviews(@PathVariable Long orderItemId, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        Response<ReviewResponseDto> response = new Response<>();
        ReviewResponseDto reviewResponseDto = reviewService.updateOrderItemReview(orderItemId,reviewRequestDto);
        response.setDto(reviewResponseDto);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("orderItem/{orderItemId}/review")
    public ResponseEntity<Response<ReviewResponseDto>> deleteOrderItemReviews(@PathVariable Long orderItemId){
        Response<ReviewResponseDto> response = new Response<>();
        reviewService.deleteOrderItemReview(orderItemId);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("business/{id}/reviews")
    public ResponseEntity<Response<PaginatedDto<ReviewResponseDto>>> getBusinessReviews(@PathVariable("id") Long businessId, @RequestBody BaseFilter baseFilter){
        Response<PaginatedDto<ReviewResponseDto>> response = new Response<>();
        Pageable pageable = baseFilter.getPageable();
        PaginatedDto<ReviewResponseDto> paginatedDto = reviewService.getBusinessReviews(List.of(businessId),pageable);
        response.setDto(paginatedDto);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("businessService/{id}/reviews")
    public ResponseEntity<Response<PaginatedDto<ReviewResponseDto>>> getBusinessServiceReviews(@PathVariable("id") Long businessServiceId, @RequestBody BaseFilter baseFilter){
        Response<PaginatedDto<ReviewResponseDto>> response = new Response<>();
        Pageable pageable = baseFilter.getPageable();
        PaginatedDto<ReviewResponseDto> paginatedDto = reviewService.getBusinessServiceReviews(List.of(businessServiceId),pageable);
        response.setDto(paginatedDto);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
