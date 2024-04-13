package com.Urban_India.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceRequest extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
     @JoinTable(name = "buyer_service_request",
             joinColumns = @JoinColumn(name = "service_request_id",referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "buyer_id",referencedColumnName = "id")
     )
     private List<User> users;

    @OneToOne
    @JoinColumn(name = "business_service_id",referencedColumnName = "id")
    private BusinessService businessService;

    @OneToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private Status status ;
    @OneToOne
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "payment_id",referencedColumnName = "id")
    private Payment payment;
}
