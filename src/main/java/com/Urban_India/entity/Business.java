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
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Address address;
    @OneToOne
    private Image image;
    //business details such as pan card,adhar card
    private String documents;

    @OneToOne
    @JoinColumn(name = "seller_id",referencedColumnName = "id")
    private User user ;

    @OneToMany(mappedBy = "business",cascade = CascadeType.ALL)
    private List<BusinessService> businessServices;
}
