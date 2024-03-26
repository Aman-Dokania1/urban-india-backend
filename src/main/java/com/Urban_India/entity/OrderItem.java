package com.Urban_India.entity;

import com.Urban_India.Enum.OrderItemStatusEnum;
import com.Urban_India.payload.OrderItemDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "business_service_id",nullable = false)
    private Long businessServiceId;

    @Column(name = "completionDate",nullable = false)
    private LocalDate completionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    @Builder.Default
    private OrderItemStatusEnum orderItemStatusEnum = OrderItemStatusEnum.PENDING;

    @Column(name = "business_service_price",nullable = false)
    private double businessServicePrice;

    private Long quantity;

    @Column(name = "business_service_name",nullable = false)
    private String businessServiceName;

    @OneToOne(mappedBy = "orderItem",cascade = {CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.LAZY,orphanRemoval = true)
    Review review;

    @Transient
//    @Builder.Default
    private Double effectivePrice ;

    @PostLoad
    private void setEffectivePrice(){
        if(Objects.nonNull(order.getCouponId())) {
            effectivePrice = this.businessServicePrice - (order.getCouponPercentage() * this.businessServicePrice)/100.00;
        }else {
            effectivePrice = this.businessServicePrice;
        }
    }

    @PostPersist
    private void setEffectivePriceAfterSave(){
        this.setEffectivePrice();
    }

    public OrderItemDto toOrdertItemDto(){
        return OrderItemDto.builder()
                .id(this.id)
                .businessServiceName(this.businessServiceName)
                .businessServiceId(this.businessServiceId)
                .completionDate(this.completionDate)
                .quantity(this.quantity)
                .businessServicePrice(this.businessServicePrice)
                .status(this.orderItemStatusEnum)
                .effectivePrice(this.effectivePrice)
                .review( Objects.nonNull(this.review) ? this.review.toReviewResponseDto() : null)
                .build();
    }
}
