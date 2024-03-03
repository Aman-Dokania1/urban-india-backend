package com.Urban_India.entity;

import com.Urban_India.payload.ReviewResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "reviews")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "business_service_id")
    private BusinessService businessService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;


    public ReviewResponseDto toReviewResponseDto(){
        return ReviewResponseDto.builder()
                .id(this.id)
                .rating(this.rating)
                .description(this.description)
                .businessServiceId(this.businessService.getId())
                .businessServiceName(this.businessService.getTitle())
                .userId(this.user.getId())
                .userName(this.user.getFirstName() + this.getUser().getLastName())
                .build();
    }

}
