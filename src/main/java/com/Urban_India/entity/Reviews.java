package com.Urban_India.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "business_service_id")
    private BusinessService businessService;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;
}
