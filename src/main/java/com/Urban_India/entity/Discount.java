package com.Urban_India.entity;

import com.Urban_India.payload.BusinessDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startTime;
    private String endTime;
    private Double percent;
    private String code;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "service_discount",
            joinColumns = @JoinColumn(name = "discount_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "business_service_id",referencedColumnName = "id")
    )
    private List<BusinessService> businessServiceList;
}
