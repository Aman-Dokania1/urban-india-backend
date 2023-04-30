package com.Urban_India.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long payment_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_service_id",referencedColumnName = "id")
    private BusinessService businessService;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id",referencedColumnName = "id")
    private Reviews review;

}
