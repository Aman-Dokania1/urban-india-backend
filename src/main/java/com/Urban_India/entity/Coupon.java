package com.Urban_India.entity;

import com.Urban_India.Enum.StatusEnum;
import com.Urban_India.payload.CouponDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startTime;
    private LocalDate endTime;
    private Double percent;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusEnum status = StatusEnum.INACTIVE;

    @Column(name = "minimum_amount")
    private Double minimumAmount;

    private String description;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "service_discount",
            joinColumns = @JoinColumn(name = "discount_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "business_service_id",referencedColumnName = "id")
    )
    private List<BusinessService> businessServiceList;

    public CouponDto toCouponDto(){
        return CouponDto.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .code(this.code)
                .percent(this.percent)
                .minimumAmount(this.minimumAmount)
                .description(this.description)
                .build();
    }
}
