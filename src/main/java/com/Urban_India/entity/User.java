package com.Urban_India.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone_number;
    private String gender;
    private String password;
    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToOne
    @JoinColumn(name = "business_id",referencedColumnName = "id")
    private Business business;

     @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
     @JoinTable(name = "users_roles",
             joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
     )
    private Set<Role> roles;

     @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
     @JoinTable(name = "buyer_service_request",
             joinColumns = @JoinColumn(name = "buyer_id",referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "service_request_id",referencedColumnName = "id")
     )
     private List<ServiceRequest> serviceRequests;

     @OneToOne(mappedBy = "user")
     private Orders order;
}
