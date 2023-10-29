package com.Urban_India.entity;

import com.Urban_India.payload.BusinessServiceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "business_service")
public class BusinessService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private Long mode_id;

    @OneToOne
    @JoinColumn(name = "discount_id",referencedColumnName = "id")
    private Coupon coupon;

    @OneToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private ServiceProviderEntitiy service;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToMany(mappedBy = "businessService",cascade = CascadeType.ALL)
    private List<ChildService> childServiceList;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "service_discount",
            joinColumns = @JoinColumn(name = "business_service_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id",referencedColumnName = "id")
    )
    private List<Coupon> couponList;

    @OneToOne(mappedBy = "businessService")
    private Orders order;

    @OneToMany(mappedBy = "businessService",cascade = CascadeType.ALL)
    List<Reviews> reviewsList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToMany(mappedBy = "businessServices")
    private List<CartItem> cartItems;

    public BusinessServiceDto toBusinessServiceDto(){
        return BusinessServiceDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .mode_id(this.mode_id)
                .image(Objects.nonNull(this.image) ? this.image.getName() : null)
                .businessName(this.business.getName())
                .businessId(this.business.getId())
                .serviceTypeId(this.service.getId())
                .serviceTypeName(this.service.getTitle())
                .statusId(Objects.nonNull(this.status) ? this.status.getId() : null)
                .addressModel(this.address.addressModel)
                .addressID(this.address.getId())
                .build();
    }
}
