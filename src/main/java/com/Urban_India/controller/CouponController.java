package com.Urban_India.controller;

import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.CouponDto;
import com.Urban_India.service.CouponService;
import com.Urban_India.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Operation(summary = "add a new Coupon to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "coupon is added successfully",
            content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = CouponDto.class))
            }), @ApiResponse(responseCode = "412",description = "validation error"),
                @ApiResponse(responseCode = "500",description = "server error")
    })
    @PostMapping
    private ResponseEntity<Response<CouponDto>> addCoupon(@RequestBody CouponDto couponDto){
        Response<CouponDto> response = new Response<>();
        try {
            response.setDto(this.couponService.addCoupon(couponDto));
            response.setHttpStatus(HttpStatus.CREATED);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }catch (UrbanApiException urbanApiException){
            response.setRejectedDto(null);
            response.setErrorMessage(urbanApiException.getMessage());
            response.setHttpStatus(HttpStatus.PRECONDITION_FAILED);
            return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);
        }catch (Exception e){
            response.setRejectedDto(null);
            response.setErrorMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "get all coupon from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "get all available coupon",
                    content = {
                            @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = CouponDto.class)))
                    }), @ApiResponse(responseCode = "500",description = "server error")
    })
    @GetMapping
    private ResponseEntity<Response<List<CouponDto>>> getAllCoupon(){
        Response<List<CouponDto>> response = new Response<>();
        try {
            response.setDto(this.couponService.getAllCoupon());
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            response.setRejectedDto(null);
            response.setErrorMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "get coupon using path variable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "coupon is added successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CouponDto.class))
                    }), @ApiResponse(responseCode = "404",description = "coupon doesn't exist with given id"),
            @ApiResponse(responseCode = "500",description = "server error")
    })
    @GetMapping("{id}")
    private ResponseEntity<Response<CouponDto>> getCouponById(@PathVariable Long id){
        Response<CouponDto> response = new Response<>();
        try {
            response.setDto(this.couponService.getCouponById(id));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (ResourceNotFoundException resourceNotFoundException){
            response.setRejectedDto(null);
            response.setErrorMessage(resourceNotFoundException.getMessage());
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            response.setRejectedDto(null);
            response.setErrorMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "update the existing coupon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "coupon is updated successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CouponDto.class))
                    }), @ApiResponse(responseCode = "500",description = "server error")
    })
    @PutMapping
    private ResponseEntity<Response<CouponDto>> updateCoupon(@RequestBody CouponDto couponDto){
        Response<CouponDto> response = new Response<>();
        try {
            response.setDto(this.couponService.updateCoupon(couponDto));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            response.setRejectedDto(null);
            response.setErrorMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "delete the existing coupon from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "coupon is deleted successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                    }), @ApiResponse(responseCode = "500",description = "server error")
    })
    @DeleteMapping
    private ResponseEntity<Boolean> deleteCoupon(@RequestBody CouponDto couponDto){
        Response<CouponDto> response = new Response<>();
        try {
            this.couponService.deleteCoupon(couponDto);
            response.setDto(null);
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(true,HttpStatus.OK);
        }catch (Exception e){
            response.setRejectedDto(null);
            response.setErrorMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
