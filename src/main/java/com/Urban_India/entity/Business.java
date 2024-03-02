package com.Urban_India.entity;

import com.Urban_India.model.DocumentsModel;
import com.Urban_India.payload.BusinessDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String tagline;

    @Column(name = "average_rating")
    @Builder.Default
    private Double averageRating = 0.0;

    @Column(name = "total_reviews")
    @Builder.Default
    private Long totalReviews = 0L;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    //business
    // details such as pan card,adhar card
    private String documents;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seller_id",referencedColumnName = "id")
    private User user ;

    @OneToMany(mappedBy = "business",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "businessEntityAReference")
    private List<BusinessService> businessServices;

    @OneToMany(mappedBy = "business",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Coupon> couponList;

    @OneToMany(mappedBy = "business",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Review> reviewsList;

//    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
//    List<Cart> cartList;

    @Transient
    DocumentsModel documentsModel;

    @PostLoad
    public void postload(){
        if(Objects.nonNull(this.documents)) {
            JSONObject jsonObject = new JSONObject(this.documents);
            String gstinNumber = jsonObject.has("gstinNumber")  ? jsonObject.getString("gstinNumber") : null;
            this.documentsModel = new DocumentsModel(gstinNumber);
        }
    }

    public BusinessDto toBusinessDto(){
        return BusinessDto.builder()
                .id(this.id)
                .name(this.name)
                .tagline(this.tagline)
                .rating(this.averageRating)
                .documentsModel(this.documentsModel)
                .addressModel(this.address.addressModel)
                .build();
    }
}
