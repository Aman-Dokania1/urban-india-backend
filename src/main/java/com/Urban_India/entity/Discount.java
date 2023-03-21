package com.Urban_India.entity;

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
    private Date startTime;
    private Date endTime;
    private Double percent;
    private String code;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "service_discount",
            joinColumns = @JoinColumn(name = "discount_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "business_service_id",referencedColumnName = "id")
    )
    private List<BusinessService> businessServiceList;
}
