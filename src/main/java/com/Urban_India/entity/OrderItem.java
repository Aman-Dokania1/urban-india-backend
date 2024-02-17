package com.Urban_India.entity;

import com.Urban_India.Enum.OrderItemStatusEnum;
import com.Urban_India.Enum.OrderStatusEnum;
import com.Urban_India.payload.OrderItemDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "order_items")
@SuperBuilder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @Column(name = "business_service_id",nullable = false)
    private Long businessServiceId;

    @Column(name = "completionDate",nullable = false)
    private LocalDate completionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    @Builder.Default
    private OrderItemStatusEnum orderItemStatusEnum = OrderItemStatusEnum.PENDING;

    @Column(name = "business_service_price")
    private double businessServicePrice;

    private Long quantity;

    @Column(name = "business_service_name")
    private String businessServiceName;

    public OrderItemDto toOrdertItemDto(){
        return OrderItemDto.builder()
                .id(this.id)
                .businessServiceName(this.businessServiceName)
                .businessServiceId(this.businessServiceId)
                .completionDate(this.completionDate)
                .quantity(this.quantity)
                .businessServicePrice(this.businessServicePrice)
                .status(this.orderItemStatusEnum)
                .build();
    }
}
