package com.Urban_India.entity;

import com.Urban_India.Enum.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "orders")
@SuperBuilder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "user_address_id",nullable = false)
    private Long userAddressId;

    @Column(name = "business_id",nullable = false)
    private Long businessId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "user_address",nullable = false)
    private String address;

    @Column(name = "business_name",nullable = false)
    private String businessName;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "coupon_percentage")
    private Double couponPercentage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    @Builder.Default
    private OrderStatusEnum orderStatusEnum = OrderStatusEnum.INPROGRESS;

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    List<OrderItem> orderItems;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "business_service_id",referencedColumnName = "id")
//    private BusinessService businessService;


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "status_id",referencedColumnName = "id")
//    private Status status;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id",referencedColumnName = "id")
//    private Address address;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "review_id",referencedColumnName = "id")
//    private Reviews review;

}
